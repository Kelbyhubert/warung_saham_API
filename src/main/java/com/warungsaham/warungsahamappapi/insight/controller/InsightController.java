package com.warungsaham.warungsahamappapi.insight.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.global.response.ApiResponse;
import com.warungsaham.warungsahamappapi.insight.dto.request.NewInsightRequest;
import com.warungsaham.warungsahamappapi.insight.dto.response.InsightDetailDto;
import com.warungsaham.warungsahamappapi.insight.dto.response.InsightDto;
import com.warungsaham.warungsahamappapi.insight.service.InsightService;

import jakarta.validation.Valid;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping(path = "/api/v1/insight")
public class InsightController {
    
    private InsightService insightService;

    public InsightController(InsightService insightService){
        this.insightService = insightService;
    }

    @GetMapping(
        path = ""
    )
    public ResponseEntity<ApiResponse<Page<InsightDto>>> getInsightPerPage(@RequestParam int index, 
                                                                            @RequestParam int size,
                                                                            @RequestParam(required = false) String title,
                                                                            @RequestParam(required = false) String createBy,
                                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    
            Page<InsightDto> pageData = insightService.getListInsightPageByFilter(index, size, title,createBy,fromDate,endDate);
        
            ApiResponse<Page<InsightDto>> apiResponse = new ApiResponse<>();
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setData(pageData);
    
            return ResponseEntity.ok(apiResponse);


    }

    @GetMapping(
        path = "/{id}"
    )
    public ResponseEntity<ApiResponse<InsightDetailDto>> getInsightDetail(@PathVariable int id) {
        InsightDetailDto data = insightService.getInsightDetailById(id);
        
        ApiResponse<InsightDetailDto> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(data);

        return ResponseEntity.ok(apiResponse);
    }
    

    @PostMapping(
        path = "/create"
    )
    public ResponseEntity<ApiResponse<String>> postMethodName(@Valid @ModelAttribute NewInsightRequest request) {
        insightService.createInsight(request);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData("Success");

        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping(
        path = "/{id}"
    )
    public ResponseEntity<ApiResponse<String>> putMethodName(@PathVariable int id,@Valid @ModelAttribute NewInsightRequest request) {
        insightService.updateInsight(id, request);
        
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData("Success");

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


    
}
