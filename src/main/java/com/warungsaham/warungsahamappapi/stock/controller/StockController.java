package com.warungsaham.warungsahamappapi.stock.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.stock.model.Stock;
import com.warungsaham.warungsahamappapi.stock.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/api/v1/stock")
public class StockController {
    
    private StockService stockService;

    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @PostMapping(
        path = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addNewStock(@RequestBody Stock stock){
        stockService.createNewStock(stock);
        return ResponseEntity.ok("Success");
    }

    @GetMapping(
        path = ""
    )
    public ResponseEntity<?> getAllStock(@RequestParam int index, @RequestParam int size, @RequestParam String search, @RequestParam String filter) {
        Page<Stock> stockPage = stockService.getStockList(index, size,search,filter);
        return ResponseEntity.ok(stockPage);
    }

    @GetMapping(
        path = "/{code}"
    )
    public ResponseEntity<?> getStockByCode(@PathVariable(value = "code") String code) {
        Stock stock = stockService.getStock(code);
        return ResponseEntity.ok(stock);
    }
    
    
}
