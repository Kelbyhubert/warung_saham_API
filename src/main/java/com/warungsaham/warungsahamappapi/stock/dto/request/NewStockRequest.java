package com.warungsaham.warungsahamappapi.stock.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewStockRequest {
    

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 4, max = 4 , message = "Invalid Format Field")
    private String stockCode;

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 3, max = 100 , message = "Invalid Field Format")
    private String company;

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 3, max = 100 , message = "Invalid Field Format")
    private String sector;


    
}
