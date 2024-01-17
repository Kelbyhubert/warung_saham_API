package com.warungsaham.warungsahamappapi.rekom.dto.response;

import java.util.Date;
import java.util.List;

import com.warungsaham.warungsahamappapi.rekom.model.RekomTarget;

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
public class RekomDetailResponse {
    
    private int id;
    private String createBy;
    private List<RekomTarget> targetList;
    private String description;
    private Date rekomDate;
    private int entryFrom;
    private int entryTo;
    private int stopLoss;
    private String rekomType;
}
