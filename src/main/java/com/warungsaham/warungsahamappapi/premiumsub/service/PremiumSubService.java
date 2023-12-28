package com.warungsaham.warungsahamappapi.premiumsub.service;

import org.springframework.web.multipart.MultipartFile;

import com.warungsaham.warungsahamappapi.premiumsub.dto.response.PaymentDataResponse;


public interface PremiumSubService {
    
    public void createPremiumUser(String userId, int planId, MultipartFile imageUrl, String paymentType);
    public PaymentDataResponse getPaymentDataById(int id);

}
