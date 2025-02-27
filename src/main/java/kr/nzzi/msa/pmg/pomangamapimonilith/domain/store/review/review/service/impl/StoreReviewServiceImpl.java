package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.common.file.service.impl.FileStorageServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.item.dao.jpa.OrderItemJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.order.item.item.model.OrderItem;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.image.model.StoreReviewImage;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.image.model.StoreReviewImageType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.like.dao.jpa.StoreReviewLikeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.service.impl.StoreReviewReplyServiceImpl;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.dao.jpa.StoreReviewJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.dto.StoreReviewDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.model.StoreReview;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.model.StoreReviewSortType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.service.ImagePath;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.service.StoreReviewService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.dao.jpa.StoreJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.store.model.Store;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StoreReviewServiceImpl implements StoreReviewService {

    StoreJpaRepository storeRepo;
    StoreReviewJpaRepository storeReviewRepo;
    StoreReviewLikeJpaRepository storeReviewLikeRepo;
    StoreReviewReplyServiceImpl storeReviewReplyService;
    UserJpaRepository userRepo;
    FileStorageServiceImpl fileStorageService;
    OrderItemJpaRepository orderItemRepo;

    @Override
    public List<StoreReviewDto> findByIdxStore(Long sIdx, Long uIdx, StoreReviewSortType sortType, Pageable pageable) {
        List<StoreReview> entities = storeReviewRepo.findByIdxStore(sIdx, sortType, pageable).getContent();
        return fromEntitiesCustom(entities, uIdx);
    }

    @Override
    public StoreReviewDto findByIdx(Long idx, Long uIdx) {
        StoreReview entity = storeReviewRepo.findByIdxAndIsActiveIsTrue(idx);
        return fromEntityCustom(entity, uIdx);
    }

    @Override
    public long count() {
        return storeReviewRepo.countByIsActiveIsTrue();
    }

    @Override
    @Transactional
    public StoreReviewDto save(StoreReviewDto dto,  List<MultipartFile> images, String idxesOrderItem) {
        // 리뷰 추가
        StoreReview entity = storeReviewRepo.save(dto.toEntity());

        // 업체 평점 재 계산, 총 리뷰 수 증가
        addAvgStar(dto.getIdxStore(), entity.getIdx());

        // 리뷰 이미지 저장
        if(images != null) {
            String imagePath = ImagePath.reviews(dto.getIdxStore(), entity.getIdx());
            List<StoreReviewImage> savedImages = saveImage(imagePath, images);
            entity.addImages(savedImages);
        }

        // order item isReviewWrite 수정
        for(String idxOrderItem : idxesOrderItem.split(",")) {
            OrderItem item = orderItemRepo.findById(Long.parseLong(idxOrderItem))
                    .orElseThrow(() -> new RuntimeException("invalid order item index"));
            item.setIsReviewWrite(true);
            orderItemRepo.save(item);
        }

        return StoreReviewDto.fromEntity(storeReviewRepo.save(entity));
    }

    @Override
    @Transactional
    public StoreReviewDto update(StoreReviewDto dto, List<MultipartFile> images) {
        // 리뷰 수정
        StoreReview entity = storeReviewRepo.findByIdxAndIsActiveIsTrue(dto.getIdx());
        entity = storeReviewRepo.save(entity.update(dto.toEntity()));

        // 리뷰 이미지 수정
        boolean isImageUpdated = dto.getIsImageUpdated() != null && dto.getIsImageUpdated().booleanValue();
        if(isImageUpdated) {
            // 기존 이미지 파일 삭제
            String imagePath = ImagePath.reviews(dto.getIdxStore(), dto.getIdx());
            fileStorageService.deleteFile(imagePath, true);
            entity.clearImages();

            if(images.size() > 0) {
                // 새로운 이미지 파일 저장
                List<StoreReviewImage> savedImages = saveImage(imagePath, images);
                entity.addImages(savedImages);
            }
        }
        return StoreReviewDto.fromEntity(storeReviewRepo.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long dIdx, Long sIdx, Long idx) {
        // 업체 평점 재 계산, 총 리뷰 수 감소
        subAvgStar(sIdx, idx);

        // 리뷰 삭제
        storeReviewRepo.deleteById(idx);

        // 리뷰 이미지 삭제
        String imagePath = ImagePath.reviews(sIdx, idx);
        fileStorageService.deleteFile(imagePath, true);
    }

    private List<StoreReviewImage> saveImage(String imagePath, List<MultipartFile> images) {
        List<StoreReviewImage> storeReviewImages = new ArrayList<>();
        for(int i=0; i<images.size(); i++) {
            MultipartFile image = images.get(i);
            String fileName = (i+1) + "." + FilenameUtils.getExtension(image.getOriginalFilename());

            // 리뷰 이미지 파일 저장
            fileStorageService.storeFile(image, imagePath, fileName);

            // 리뷰 이미지 DB 저장
            storeReviewImages.add(StoreReviewImage.builder()
                    .imagePath(imagePath + fileName)
                    .imageType(i==0 ? StoreReviewImageType.MAIN : StoreReviewImageType.SUB)
                    .sequence(i+1)
                    .build());
        }
        return storeReviewImages;
    }

    /**
     * 업체 평점 재계산 (add)
     */
    private void addAvgStar(Long sIdx, Long rIdx) {
        Store store = storeRepo.findByIdxAndIsActiveIsTrue(sIdx);
        StoreReview storeReview = storeReviewRepo.findByIdxAndIsActiveIsTrue(rIdx);
        store.addCntReview(storeReview.getStar());
        storeRepo.save(store);
    }

    /**
     * 업체 평점 재계산 (sub)
     */
    private void subAvgStar(Long sIdx, Long rIdx) {
        Store store = storeRepo.findByIdxAndIsActiveIsTrue(sIdx);
        StoreReview storeReview = storeReviewRepo.findByIdxAndIsActiveIsTrue(rIdx);
        store.subCntReview(storeReview.getStar());
        storeRepo.save(store);
    }

    /**
     * entity -> dto 변환
     * 익명처리 핸들링
     *
     * @param entities 엔티티 리스트
     * @return dto 리스트
     */
    private List<StoreReviewDto> fromEntitiesCustom(List<StoreReview> entities, Long uIdx) {
        List<StoreReviewDto> dtos = new ArrayList<>();
        for(StoreReview entity : entities) {
            StoreReviewDto dto = fromEntityCustom(entity, uIdx);
            if(dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }

    /**
     * entity -> dto 변환
     * 익명처리 핸들링
     * Todo. "익명" -> Globalization 상수 처리
     *
     * @param entity 변환할 엔티티
     * @return dto 반환. 만약 User 가 존재하지 않는다면 null 반환
     */
    private StoreReviewDto fromEntityCustom(StoreReview entity, Long uIdx) {
        StoreReviewDto dto = StoreReviewDto.fromEntity(entity);
        User user = userRepo.findByIdxAndIsActiveIsTrue(entity.getIdxUser());
        if (uIdx != null && uIdx.compareTo(user.getIdx()) == 0) {  // isOwn 처리
            dto.setIsOwn(true);
            dto.setIsLike(storeReviewLikeRepo.existsByIdxUserAndIdxStoreReview(uIdx, entity.getIdx()));
            if (entity.getIsAnonymous()) { // anonymous 처리
                dto.setNickname("나 (익명)");
            } else {
                dto.setNickname(user.getNickname());
            }
        } else {
            dto.setIsOwn(false);
            dto.setIsLike(false);
            if (entity.getIsAnonymous()) { // anonymous 처리
                dto.setNickname("익명");
            } else {
                dto.setNickname(user.getNickname());
            }
        }
        return dto;
    }
}
