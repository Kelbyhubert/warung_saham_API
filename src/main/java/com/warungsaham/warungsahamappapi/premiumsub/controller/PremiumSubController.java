package com.warungsaham.warungsahamappapi.premiumsub.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.premiumsub.dto.request.CreatePremiumRequest;
import com.warungsaham.warungsahamappapi.premiumsub.dto.response.PaymentDataResponse;
import com.warungsaham.warungsahamappapi.premiumsub.service.PremiumSubService;




@RestController
@RequestMapping(path = "/api/v1/premium")
public class PremiumSubController {

    private PremiumSubService premiumSubService;

    @Autowired
    public PremiumSubController(PremiumSubService premiumSubService){
        this.premiumSubService = premiumSubService;
    }
    
    @PostMapping(
        path = "/{userId}/create"
    )
    public ResponseEntity<?> createPremiumUser(@PathVariable(value = "userId") String userId, @ModelAttribute CreatePremiumRequest createPremiumRequest){
        premiumSubService.createPremiumUser(userId, createPremiumRequest.getPlanId(), createPremiumRequest.getImage(), createPremiumRequest.getPaymentType());
        return ResponseEntity.ok("Success");
    }

    @GetMapping(
        path = "/{id}/payment"
    )
    public ResponseEntity<?> getPaymentDataById(@PathVariable(value = "id") int id){
        PaymentDataResponse result = premiumSubService.getPaymentDataById(id);
        return ResponseEntity.ok(result);
    }
    
}
