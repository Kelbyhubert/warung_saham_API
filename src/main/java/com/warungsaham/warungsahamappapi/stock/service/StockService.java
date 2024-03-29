package com.warungsaham.warungsahamappapi.stock.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.warungsaham.warungsahamappapi.stock.model.Stock;

public interface StockService {
    
    public void createNewStock(Stock stock);
    public Page<Stock> getStockList(int index, int size ,String search , String filter);
    public List<Stock> getStockListContainStockCode(String stockCode); 
    public Stock getStock(String code);
    
}
