package com.warungsaham.warungsahamappapi.rekom.dto.request;

import java.util.Date;
import java.util.List;


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
    
    private String createBy;
    private String rekomCode;
    private List<TargetRequest> targetList;
    private String description;
    private Date rekomDate;
    private int entryFrom;
    private int entryTo;
    private int stopLoss;
    private String rekomType;

}
