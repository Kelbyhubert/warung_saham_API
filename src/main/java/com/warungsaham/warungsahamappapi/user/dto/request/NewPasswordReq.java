package com.warungsaham.warungsahamappapi.user.dto.request;

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
public class NewPasswordReq {

    private String oldPassword;

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 8,max = 20, message = "Invalid Format")
    private String newPassword;
}
