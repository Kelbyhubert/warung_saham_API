package com.warungsaham.warungsahamappapi.rekom.dto.request;

import jakarta.validation.constraints.NotEmpty;
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
public class TargetRequest {

    private int id;
    
    @NotNull(message = "Invalid Mandatory Field")
    private Integer targetFrom;

    @NotNull(message = "Invalid Mandatory Field")
    private Integer targetTo;

    @NotNull(message = "Invalid Mandatory Field")
    private Integer orders;

    @NotNull(message = "Invalid Mandatory Field")
    private Integer status;
}
