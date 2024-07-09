package com.warungsaham.warungsahamappapi.premiumsub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.global.response.ApiResponse;
import com.warungsaham.warungsahamappapi.premiumsub.dto.request.CreatePremiumRequest;
import com.warungsaham.warungsahamappapi.premiumsub.dto.response.PaymentDataResponse;
import com.warungsaham.warungsahamappapi.premiumsub.service.PremiumSubService;

@RestController
@RequestMapping(path = "/api/v1/premium")
public class PremiumSubController {

    private PremiumSubService premiumSubService;

    public PremiumSubController(PremiumSubService premiumSubService){
        this.premiumSubService = premiumSubService;
    }
    
    @PostMapping(
        path = "/{userId}/create",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<String>> createPremiumUser(@PathVariable(value = "userId") String userId, @ModelAttribute CreatePremiumRequest createPremiumRequest){
        premiumSubService.createPremiumUser(userId, createPremiumRequest.getPlanId(), createPremiumRequest.getImage(), createPremiumRequest.getPaymentType());

        ApiResponse<String> response = new ApiResponse<>();
        response.setData("Success");
        response.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping(
        path = "/{id}/payment",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<PaymentDataResponse>> getPaymentDataById(@PathVariable(value = "id") int id){
        
        PaymentDataResponse result = premiumSubService.getPaymentDataById(id);

        ApiResponse<PaymentDataResponse> response = new ApiResponse<>();
        response.setData(result);
        response.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
}
