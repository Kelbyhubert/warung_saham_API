package com.warungsaham.warungsahamappapi.rekom.dto.response;

import java.util.Date;

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
public class RekomListResponse {

    private int id;
    private String stockCode;
    private String createBy;
    private Date rekomDate;
    private int entryFrom;
    private int entryTo;
    private String target;
    private int stopLoss;
    private String rekomType;
    
}
