package com.warungsaham.warungsahamappapi.user.dto.request;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NewUserReq {
    
    @NotBlank(message = "Invalid Mandatory Field")
    @Size(max = 100,min = 3, message = "Invalid Format Mandatory Field")
    private String username;

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 5, max = 100, message = "Invalid Format Mandatory Field")
    @Email(message = "Invalid Format Mandatory Field")
    private String email;

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 3, max = 100, message = "Invalid Format Mandatory Field")
    private String name;

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 8,max = 20, message = "Invalid Format Mandatory Field")
    private String phoneNumber;

    @NotNull(message = "Invalid Mandatory Field")
    private Date dob;

    private List<Integer> roleIdList;

}
