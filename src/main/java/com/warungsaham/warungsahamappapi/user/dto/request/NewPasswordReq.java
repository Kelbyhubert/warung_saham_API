package com.warungsaham.warungsahamappapi.user.dto.request;

import jakarta.validation.constraints.NotNull;
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
public class NewPasswordReq {

    private String oldPassword;

    @NotNull
    private String newPassword;
}
