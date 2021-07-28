package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.dao.jpa.CouponJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.dto.CouponDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.exception.CouponException;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.model.Coupon;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.service.CouponService;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {

    CouponJpaRepository couponRepo;

    @Override
    public List<CouponDto> findAllByIdxUser(Long uIdx) {
        return CouponDto.fromEntities(couponRepo.findByUser_IdxAndIsActiveIsTrueOrderByIsUsedAscIdxDesc(uIdx));
    }

    @Override
    public CouponDto findOneByIdx(Long cIdx) {
        return CouponDto.fromEntity(couponRepo.findByIdxAndIsActiveIsTrue(cIdx));
    }

    @Override
    public CouponDto findOneByCode(String code) {
        return CouponDto.fromEntity(couponRepo.findByCodeAndIsActiveIsTrueAndUserIsNull(code.replaceAll("-", ""))
                .orElseThrow(() -> new CouponException("invalid coupon code")));
    }

    @Override
    public CouponDto saveOneByCode(String code, User user) {
        Coupon coupon = couponRepo.findByCodeAndIsActiveIsTrueAndUserIsNull(code.replaceAll("-", ""))
                .orElseThrow(() -> new CouponException("invalid coupon code"));
        coupon.setUser(user);
        return CouponDto.fromEntity(couponRepo.save(coupon));
    }
}

