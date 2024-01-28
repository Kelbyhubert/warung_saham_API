package com.warungsaham.warungsahamappapi.user.dto.response;

import java.util.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRes {
    private String token;
    private String refreshToken;
    private Date expiredDate;
}
