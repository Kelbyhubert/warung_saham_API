package com.warungsaham.warungsahamappapi.insight.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.warungsaham.warungsahamappapi.insight.dto.request.NewInsightRequest;
import com.warungsaham.warungsahamappapi.insight.dto.response.InsightDetailDto;
import com.warungsaham.warungsahamappapi.insight.dto.response.InsightDto;

public interface InsightService {
    
    public void createInsight(NewInsightRequest newInsightRequest);
    public void updateInsight(int id , NewInsightRequest newInsightRequest);
    public Page<InsightDto> getListInsightPage(int index, int size);
    public Page<InsightDto> getListInsightPageByFilter(int index, int size, String title, String createBy, Date startDate, Date endDate);
    public InsightDetailDto getInsightDetailById(int id);
    
}
