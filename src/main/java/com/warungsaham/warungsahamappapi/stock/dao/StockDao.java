package com.warungsaham.warungsahamappapi.stock.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.stock.model.Stock;

@Repository
public interface StockDao extends JpaRepository<Stock,String>{
    
    Page<Stock> findAllByStockCodeOrCompany(String stockCode, String company, Pageable pageable);
    Stock findByStockCode(String code);
}
