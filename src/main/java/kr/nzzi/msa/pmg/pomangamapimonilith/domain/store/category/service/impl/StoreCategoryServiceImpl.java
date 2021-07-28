package kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.category.service.impl;

import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.category.dao.jpa.StoreCategoryJpaRepository;
import kr.nzzi.msa.pmg.pomangamapimonilith.domain.store.category.service.StoreCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreCategoryServiceImpl implements StoreCategoryService {
    
    StoreCategoryJpaRepository storeCategoryRepo;
}
