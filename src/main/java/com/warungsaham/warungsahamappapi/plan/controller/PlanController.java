package com.warungsaham.warungsahamappapi.plan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.global.response.ApiResponse;
import com.warungsaham.warungsahamappapi.plan.model.Plan;
import com.warungsaham.warungsahamappapi.plan.service.PlanService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/api/v1/plan")
public class PlanController {

    private PlanService planService;

    public PlanController(PlanService planService){
        this.planService = planService;
    }
    
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<Plan>>> getPlanList() {
        List<Plan> planList = planService.getAllPlan();
        ApiResponse<List<Plan>> apiResponse = new ApiResponse<>();
        apiResponse.setData(planList);
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
    
}
