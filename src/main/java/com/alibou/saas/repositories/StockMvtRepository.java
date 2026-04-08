package com.alibou.saas.repositories;

import com.alibou.saas.entities.StockMvt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMvtRepository extends JpaRepository<StockMvt, String> {

    Page<StockMvt> findAllByProductId(String productId, Pageable pageable);
}
