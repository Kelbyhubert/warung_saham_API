package com.warungsaham.warungsahamappapi.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginReq {

    @NotNull(message = "Invalid Mandatory Field")
    private String username;

    @NotNull(message = "Invalid Mandatory Field")
    private String password;
}
