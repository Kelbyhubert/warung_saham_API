package com.warungsaham.warungsahamappapi.user.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginReq {
    private String username;
    private String password;
}
