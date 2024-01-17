package com.warungsaham.warungsahamappapi.rekom.service;


import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.warungsaham.warungsahamappapi.global.exception.NotFoundException;
import com.warungsaham.warungsahamappapi.rekom.dao.RekomDao;
import com.warungsaham.warungsahamappapi.rekom.dto.request.SaveUpdateRekomRequest;
import com.warungsaham.warungsahamappapi.rekom.dto.request.TargetRequest;
import com.warungsaham.warungsahamappapi.rekom.dto.response.RekomListResponse;
import com.warungsaham.warungsahamappapi.rekom.model.Rekom;
import com.warungsaham.warungsahamappapi.rekom.model.RekomTarget;
import com.warungsaham.warungsahamappapi.stock.dao.StockDao;
import com.warungsaham.warungsahamappapi.stock.model.Stock;
import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.transaction.Transactional;

@Service
public class RekomServiceImpl implements RekomService {

    private RekomDao rekomDao;
    private UserDao userDao;
    private StockDao stockDao;

    @Autowired
    public RekomServiceImpl(RekomDao rekomDao, UserDao userDao ,StockDao stockDao){
        this.rekomDao = rekomDao;
        this.userDao = userDao;
        this.stockDao = stockDao;
    }

    @Override
    @Transactional
    public void createRekom(SaveUpdateRekomRequest newRekomRequest) {

        User user = userDao.findByUsername(newRekomRequest.getCreateBy());

        if(user == null){
            throw new NotFoundException("User Not Found");
        }

        Stock stock = stockDao.findByStockCode(newRekomRequest.getRekomCode());
        
        if(stock == null){
            throw new NotFoundException("Stock Not Found");
        }

        Rekom rekom = new Rekom();
        rekom.setDescription(newRekomRequest.getDescription());
        rekom.setEntryFrom(newRekomRequest.getEntryFrom());
        rekom.setEntryTo(newRekomRequest.getEntryTo());
        rekom.setRekomDate(newRekomRequest.getRekomDate());
        rekom.setRekomType(newRekomRequest.getRekomType());
        rekom.setStopLoss(newRekomRequest.getStopLoss());
        rekom.setStock(stock);
        rekom.setUser(user);



        for(TargetRequest tReq : newRekomRequest.getTargetList()) {
            RekomTarget rekomTarget = new RekomTarget();
            rekomTarget.setId(0);
            rekomTarget.setOrders(tReq.getOrders());
            rekomTarget.setRekom(rekom);
            rekomTarget.setStatus(tReq.getStatus());
            rekomTarget.setTargetFrom(tReq.getTargetFrom());
            rekomTarget.setTargetTo(tReq.getTargetTo());
            rekom.getTarget().add(rekomTarget);
        }


        rekomDao.save(rekom);
        
    }

    @Override
    public Page<RekomListResponse> getRekomList(int index, int size, String search) {
        Pageable pageable = PageRequest.of(index, size);
        Page<RekomListResponse> pageRekom = rekomDao.findAll(pageable).map(new Function<Rekom,RekomListResponse>() {

            @Override
            public RekomListResponse apply(Rekom t) {
                // TODO Auto-generated method stub
                RekomListResponse data = new RekomListResponse();
                data.setId(t.getId());
                data.setCreateBy(t.getUser().getUsername());
                data.setStockCode(t.getStock().getStockCode());
                data.setEntryFrom(t.getEntryFrom());
                data.setEntryTo(t.getEntryTo());
                data.setRekomDate(t.getRekomDate());
                data.setRekomType(t.getRekomType());
                data.setStopLoss(t.getStopLoss());
                String target = "";
                for (RekomTarget rT : t.getTarget()) {
                    target += ""+ rT.getTargetFrom() + " - " + rT.getTargetTo() + " ,";
                }
                data.setTarget(target);
                

                return data;
            }
            
        });

        return pageRekom;
    }

    @Override
    @Transactional
    public void updateRekom(int rekomId, SaveUpdateRekomRequest newRekomRequest) {

        Rekom rekom = rekomDao.findById(rekomId).orElseThrow(
            () -> new NotFoundException("Rekom Not Found")
        );

        Stock stock = stockDao.findByStockCode(newRekomRequest.getRekomCode());
        
        if(stock == null){
            throw new NotFoundException("Stock Not Found");
        }

        rekom.setDescription(newRekomRequest.getDescription());
        rekom.setEntryFrom(newRekomRequest.getEntryFrom());
        rekom.setEntryTo(newRekomRequest.getEntryTo());
        rekom.setRekomType(newRekomRequest.getRekomType());
        rekom.setStock(stock);
        rekom.setStopLoss(newRekomRequest.getStopLoss());
        
        Map<Integer,RekomTarget> rekomTargets = rekom.getTarget().stream().collect(Collectors.toMap(RekomTarget::getId, Function.identity()));

        updateRekomTarget(newRekomRequest, rekom, rekomTargets);

        rekomDao.save(rekom);

    }

    @Override
    public Rekom getRekomDetail(int rekomId) {
        Rekom rekom = rekomDao.findById(rekomId).orElseThrow(() -> new NotFoundException("Rekom Not Found"));
        return rekom;
    }

    @Override
    public void deleteRekom(int rekomId) {
        rekomDao.findById(rekomId).orElseThrow(() -> new NotFoundException("Rekom Not Found"));
        rekomDao.deleteById(rekomId);
    }

    private void updateRekomTarget(SaveUpdateRekomRequest newRekomRequest, Rekom rekom, Map<Integer, RekomTarget> rekomTargets) {
        for(TargetRequest tReq : newRekomRequest.getTargetList()) {
            int targetId = tReq.getId();
            RekomTarget rekomTarget = new RekomTarget();
            if(rekomTargets.containsKey(targetId)){
                RekomTarget existingTarget = rekomTargets.get(targetId);
                existingTarget.setOrders(tReq.getOrders());
                existingTarget.setRekom(rekom);
                existingTarget.setStatus(tReq.getStatus());
                existingTarget.setTargetFrom(tReq.getTargetFrom());
                existingTarget.setTargetTo(tReq.getTargetTo());
            }else{
                rekomTarget.setOrders(tReq.getOrders());
                rekomTarget.setRekom(rekom);
                rekomTarget.setStatus(tReq.getStatus());
                rekomTarget.setTargetFrom(tReq.getTargetFrom());
                rekomTarget.setTargetTo(tReq.getTargetTo());
                rekom.getTarget().add(rekomTarget);
            }
        }

        rekom.getTarget().removeIf(target -> !newRekomRequest.getTargetList().stream().anyMatch(data -> data.getId() == target.getId()));
        
    }

    
}
