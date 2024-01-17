package com.warungsaham.warungsahamappapi.stock.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.warungsaham.warungsahamappapi.global.exception.NotFoundException;
import com.warungsaham.warungsahamappapi.stock.dao.StockDao;
import com.warungsaham.warungsahamappapi.stock.model.Stock;

import jakarta.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {

    private StockDao stockDao;

    @Autowired
    public StockServiceImpl(StockDao stockDao){
        this.stockDao = stockDao;
    }

    @Override
    @Transactional
    public void createNewStock(Stock stock) {
        stockDao.save(stock);

    }

    @Override
    public Stock getStock(String code) {
        // TODO Auto-generated method stub
        Stock stock = stockDao.findByStockCode(code);

        if(stock == null){
            throw new NotFoundException("Code Not Found");
        }
        
        return stock;

    }

    @Override
    public Page<Stock> getStockList(int index, int size, String search, String filter) {
        Page<Stock> stockPage = null;
        Pageable pageable = PageRequest.of(index, size);
        if((search == null || search.trim().length() < 1)|| (filter == null || filter.trim().length() < 1)){
            stockPage = stockDao.findAll(pageable);
        }else{
            stockPage = stockDao.findAllByStockCodeOrCompany(search,search,pageable);
        }

        if(filter != null || filter.trim().equals("")){
            Page<Stock> filterResult = new PageImpl<Stock>(stockPage.stream().
                                                    filter(s -> s.getSector().equals(filter))
                                                    .collect(Collectors.toList()),
                                                    pageable,
                                                    pageable.getPageSize());
            return filterResult;
        }

        return stockPage;
        
    }
    
}
