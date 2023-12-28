package com.warungsaham.warungsahamappapi.premiumsub.dto.response;

import java.util.Date;



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
public class PaymentDataResponse {

    private Date paymentDate;
    private String imageUrl;
    
}
