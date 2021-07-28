package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.service;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.coupon.coupon.dto.CouponDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.user.model.User;

import java.util.List;

public interface CouponService {
    List<CouponDto> findAllByIdxUser(Long uIdx);
    CouponDto findOneByIdx(Long cIdx);
    CouponDto findOneByCode(String code);
    CouponDto saveOneByCode(String code, User user);
}
