package com.warungsaham.warungsahamappapi.rekom.dto.request;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class SaveUpdateRekomRequest {
    
    @NotBlank(message = "Invalid Mandatory Field")
    private String createBy;

    @NotBlank(message = "Invalid Mandatory Field")
    @Size(min = 4,max = 6, message = "Invalid Mandatory Field Format")
    private String rekomCode;

    private List<@Valid TargetRequest> targetList;

    private String description;

    private Date rekomDate;

    @NotNull(message = "Invalid Mandatory Field")
    private Integer entryFrom;

    @NotNull(message = "Invalid Mandatory Field")
    private Integer entryTo;

    @NotNull(message = "Invalid Mandatory Field")
    private Integer stopLoss;

    @NotBlank(message = "Invalid Mandatory Field")
    private String rekomType;

}
