package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.like.dao.jpa.StoreReviewReplyLikeJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.dao.jpa.StoreReviewReplyJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.dto.StoreReviewReplyDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.model.StoreReviewReply;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.reply.reply.service.StoreReviewReplyService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.dao.jpa.StoreReviewJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.review.review.model.StoreReview;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.dao.jpa.UserJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StoreReviewReplyServiceImpl implements StoreReviewReplyService {

    StoreReviewReplyJpaRepository storeReviewReplyRepo;
    UserJpaRepository userRepo;
    StoreReviewJpaRepository storeReviewRepo;
    StoreReviewReplyLikeJpaRepository storeReviewReplyLikeRepo;

    @Override
    public List<StoreReviewReplyDto> findByIdxStoreReview(Long rIdx, Long uIdx, Pageable pageable) {
        List<StoreReviewReply> entities = storeReviewReplyRepo.findByIdxStoreReviewAndIsActiveIsTrue(rIdx, pageable).getContent();
        return fromEntitiesCustom(entities, uIdx);
    }

    @Override
    public StoreReviewReplyDto findByIdx(Long idx, Long uIdx) {
        StoreReviewReply entity = storeReviewReplyRepo.findByIdxAndIsActiveIsTrue(idx);
        return fromEntityCustom(entity, uIdx);
    }

    @Override
    public long count() {
        return storeReviewReplyRepo.countByIsActiveIsTrue();
    }

    @Override
    @Transactional
    public StoreReviewReplyDto save(StoreReviewReplyDto dto) {
        // 댓글 추가
        StoreReviewReply entity = storeReviewReplyRepo.save(dto.toEntity());

        // 총 댓글 수 증가
        addCntReply(dto.getIdxStoreReview());

        return StoreReviewReplyDto.fromEntity(entity);
    }

    @Override
    @Transactional
    public StoreReviewReplyDto update(StoreReviewReplyDto dto) {
        // 댓글 수정
        StoreReviewReply entity = storeReviewReplyRepo.findByIdxAndIsActiveIsTrue(dto.getIdx());
        return StoreReviewReplyDto.fromEntity(storeReviewReplyRepo.save(entity.update(dto.toEntity())));
    }

    @Override
    @Transactional
    public void delete(Long rIdx, Long idx) {
        // 총 댓글 수 감소
        subCntReply(rIdx);

        // 댓글 삭제
        storeReviewReplyRepo.deleteById(idx);
    }

    /**
     * 리뷰 댓글 수 증가
     */
    private void addCntReply(Long idxReview) {
        StoreReview storeReview = storeReviewRepo.findByIdxAndIsActiveIsTrue(idxReview);
        storeReview.addCntReply();
        storeReviewRepo.save(storeReview);
    }

    /**
     * 리뷰 댓글 수 감소
     */
    private void subCntReply(Long idxReview) {
        StoreReview storeReview = storeReviewRepo.findByIdxAndIsActiveIsTrue(idxReview);
        storeReview.subCntReply();
        storeReviewRepo.save(storeReview);
    }

    /**
     * entity -> dto 변환
     * 익명처리 핸들링
     *
     * @param entities 엔티티 리스트
     * @return dto 리스트
     */
    private List<StoreReviewReplyDto> fromEntitiesCustom(List<StoreReviewReply> entities, Long uIdx) {
        List<StoreReviewReplyDto> dtos = new ArrayList<>();
        for(StoreReviewReply entity : entities) {
            StoreReviewReplyDto dto = fromEntityCustom(entity, uIdx);
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
    private StoreReviewReplyDto fromEntityCustom(StoreReviewReply entity, Long uIdx) {
        StoreReviewReplyDto dto = StoreReviewReplyDto.fromEntity(entity);
        User user = userRepo.findByIdxAndIsActiveIsTrue(entity.getIdxUser());
        if (uIdx != null && uIdx.compareTo(user.getIdx()) == 0) {  // isOwn 처리
            dto.setIsOwn(true);
            dto.setIsLike(storeReviewReplyLikeRepo.existsByIdxUserAndIdxStoreReviewReply(uIdx, entity.getIdx()));
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
