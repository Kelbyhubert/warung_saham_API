package com.warungsaham.warungsahamappapi.premiumsub.service;

import org.springframework.web.multipart.MultipartFile;

public interface PremiumSubService {
    
    public void createPremiumUser(String userId, int planId, MultipartFile imageUrl, String paymentType);

}
