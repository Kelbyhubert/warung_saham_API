package com.warungsaham.warungsahamappapi.stock.controller;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.global.response.ApiResponse;
import com.warungsaham.warungsahamappapi.stock.dto.request.NewStockRequest;
import com.warungsaham.warungsahamappapi.stock.model.Stock;
import com.warungsaham.warungsahamappapi.stock.service.StockService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/api/v1/stock")
public class StockController {
    
    private StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @PostMapping(
        path = "/add",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<String>> addNewStock(@Valid @RequestBody NewStockRequest stock){
        stockService.createNewStock(stock);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData("Success");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(
        path = "/pageable"
    )
    public ResponseEntity<ApiResponse<Page<Stock>>> getStockListPage(@RequestParam int index, 
                                                                    @RequestParam int size, 
                                                                    @RequestParam String search, 
                                                                    @RequestParam String filter) {
                                                                        
        Page<Stock> stockPage = stockService.getStockList(index, size,search,filter);

        ApiResponse<Page<Stock>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(stockPage);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(
        path = "/{code}"
    )
    public ResponseEntity<ApiResponse<Stock>> getStockByCode(@PathVariable(value = "code") String code) {
        Stock stock = stockService.getStock(code);

        ApiResponse<Stock> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(stock);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(
        path = "/list"
    )
    public ResponseEntity<ApiResponse<List<Stock>>> getAllStock(@RequestParam String stockCodeContain) {
        List<Stock> stockList = stockService.getStockListContainStockCode(stockCodeContain);

        ApiResponse<List<Stock>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(stockList);

        return ResponseEntity.ok(apiResponse);
    }
    
    
    
}
