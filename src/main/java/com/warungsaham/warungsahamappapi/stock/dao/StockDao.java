package com.warungsaham.warungsahamappapi.stock.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.stock.model.Stock;

@Repository
public interface StockDao extends JpaRepository<Stock,String>{
    
    Page<Stock> findAllByStockCodeOrCompany(String stockCode, String company, Pageable pageable);
    List<Stock> findAllByStockCodeContaining(String stockCode);
    Stock findByStockCode(String code);
}
