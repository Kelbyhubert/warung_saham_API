package com.warungsaham.warungsahamappapi.insight.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsightDetailDto {

    private int id;
    
    private String userId;

    private String title;

    private String thumbnailImg;

    private String content;

    private String createBy;

    private String updateBy;

    private Date createDate;

    private Date updateDate;

}