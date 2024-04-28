package com.warungsaham.warungsahamappapi.rekom.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.warungsaham.warungsahamappapi.rekom.dto.request.SaveUpdateRekomRequest;
import com.warungsaham.warungsahamappapi.rekom.dto.response.RekomListResponse;
import com.warungsaham.warungsahamappapi.rekom.model.Rekom;

public interface RekomService {
    
    public void createRekom(SaveUpdateRekomRequest newRekomRequest);
    public Page<RekomListResponse> getRekomList(int index, int size, String search);
    public Page<RekomListResponse> getRekomPageByFilter(int index, int size, String stockCode, Date startDate, Date endDate);
    public void updateRekom(int rekomId, SaveUpdateRekomRequest newRekomRequest);
    public Rekom getRekomDetail(int rekomId);
    public void deleteRekom(int rekomId);

}
