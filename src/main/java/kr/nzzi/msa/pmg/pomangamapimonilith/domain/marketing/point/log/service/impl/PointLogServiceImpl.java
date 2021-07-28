package kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.dao.jpa.PointLogJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.dto.PointLogDto;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.model.PointLog;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.model.PointType;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.marketing.point.log.service.PointLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PointLogServiceImpl implements PointLogService {

    PointLogJpaRepository pointLogRepo;

    public int findByIdxUser(Long uIdx) {
        int totalPoint = 0;
        List<PointLog> pointLogs = pointLogRepo.findByIdxUser(uIdx);
        for(PointLog pointLog : pointLogs) {
            if(pointLog.isValid()) {
                totalPoint = postPoint(pointLog.getPointType(), totalPoint, pointLog.getPoint());
            }
        }
        return totalPoint < 0 ? 0 : totalPoint;
    }

    public List<PointLogDto> findByIdxUser(Long uIdx, Pageable pageable) {
        return PointLogDto.fromEntities(pointLogRepo.findByIdxUser(uIdx, pageable).getContent());
    }

    public PointLogDto save(PointLog pointLog) {
        int totalPoint = findByIdxUser(pointLog.getIdxUser());
        pointLog.setPostPoint(postPoint(pointLog.getPointType(), totalPoint, pointLog.getPoint()));
        return PointLogDto.fromEntity(pointLogRepo.save(pointLog));
    }

    public int postPoint(PointType type, int totalPoint, int point) {
        switch (type) {
            case ISSUED_BY_PROMOTION:
            case ISSUED_BY_BUY:
            case UPDATED_PLUS_BY_ADMIN:
            case ROLLBACK_ISSUED_BY_PAYMENT_CANCEL:
                totalPoint += point;
                break;
            case ROLLBACK_SAVED_BY_PAYMENT_CANCEL:
            case USED_BY_BUY:
            case UPDATED_MINUS_BY_ADMIN:
                totalPoint -= point;
                break;
        }
        return totalPoint < 0 ? 0 : totalPoint;
    }
}
