package com.warungsaham.warungsahamappapi.insight.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewInsightRequest {
    
    @NotBlank
    private String userId;

    @NotBlank
    private String title;

    
    private MultipartFile thumbnailImg;

    @NotBlank
    private String content;

}
