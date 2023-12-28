package com.warungsaham.warungsahamappapi.premiumsub.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePremiumRequest {
    private int planId;
    private MultipartFile image;
    private String paymentType;
}
