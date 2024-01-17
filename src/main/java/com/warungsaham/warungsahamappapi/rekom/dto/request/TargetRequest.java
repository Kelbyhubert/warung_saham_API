package com.warungsaham.warungsahamappapi.rekom.dto.request;

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
    
    private int targetFrom;

    private int targetTo;

    private int orders;

    private int status;
}
