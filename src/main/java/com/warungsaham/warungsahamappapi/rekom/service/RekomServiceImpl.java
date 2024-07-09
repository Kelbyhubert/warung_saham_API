package com.warungsaham.warungsahamappapi.rekom.service;


import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(RekomServiceImpl.class);

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

        LOG.info("User : {}", user);

        Stock stock = stockDao.findByStockCode(newRekomRequest.getRekomCode());
        
        if(stock == null){
            throw new NotFoundException("Stock Not Found");
        }

        LOG.info("Stock : {}", stock);
        LOG.info("Creating New Rekom");

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

        LOG.info("New Rekom : {}", rekom);

        rekomDao.save(rekom);
        
    }

    @Override
    @Transactional
    public void updateRekom(int rekomId, SaveUpdateRekomRequest newRekomRequest) {

        Rekom rekom = rekomDao.findById(rekomId).orElseThrow(
            () -> new NotFoundException("Rekom Not Found")
        );

        LOG.info("Rekom : {}", rekom);

        Stock stock = stockDao.findByStockCode(newRekomRequest.getRekomCode());
        
        if(stock == null){
            throw new NotFoundException("Stock Not Found");
        }

        LOG.info("Stock : {}", stock);

        LOG.info("Updating Rekom id : {}", rekom.getId());

        rekom.setDescription(newRekomRequest.getDescription());
        rekom.setEntryFrom(newRekomRequest.getEntryFrom());
        rekom.setEntryTo(newRekomRequest.getEntryTo());
        rekom.setRekomType(newRekomRequest.getRekomType());
        rekom.setStock(stock);
        rekom.setStopLoss(newRekomRequest.getStopLoss());
        
        Map<Integer,RekomTarget> rekomTargets = rekom.getTarget().stream().collect(Collectors.toMap(RekomTarget::getId, Function.identity()));

        updateRekomTarget(newRekomRequest, rekom, rekomTargets);

        LOG.info("Updated Rekom : {} ", rekom);

        rekomDao.save(rekom);

    }

    @Override
    public Rekom getRekomDetail(int rekomId) {
        return rekomDao.findById(rekomId).orElseThrow(() -> new NotFoundException("Rekom Not Found"));
    }

    @Override
    public void deleteRekom(int rekomId) {
        rekomDao.findById(rekomId).orElseThrow(() -> new NotFoundException("Rekom Not Found"));
        rekomDao.deleteById(rekomId);
    }


    private void updateRekomTarget(SaveUpdateRekomRequest newRekomRequest, Rekom rekom, Map<Integer, RekomTarget> rekomTargets) {
        
        LOG.info("Updating Rekom id {} Target" , rekom.getId());

        //Delete target that not at new target
        LOG.info("Remove non existing updated target in current target");
        rekom.getTarget().removeIf(target -> newRekomRequest.getTargetList().stream().noneMatch(data -> data.getId() == target.getId()));
        
        //update target
        for(TargetRequest tReq : newRekomRequest.getTargetList()) {
            int targetId = tReq.getId();
            if(rekomTargets.containsKey(targetId)){
                //update existed target
                RekomTarget existingTarget = rekomTargets.get(targetId);
                existingTarget.setOrders(tReq.getOrders());
                existingTarget.setRekom(rekom);
                existingTarget.setStatus(tReq.getStatus());
                existingTarget.setTargetFrom(tReq.getTargetFrom());
                existingTarget.setTargetTo(tReq.getTargetTo());

                LOG.info("Updated Target id {} : {}", existingTarget.getId(),existingTarget);
            }else{
                // create new target
                RekomTarget rekomTarget = new RekomTarget();
                rekomTarget.setOrders(tReq.getOrders());
                rekomTarget.setRekom(rekom);
                rekomTarget.setStatus(tReq.getStatus());
                rekomTarget.setTargetFrom(tReq.getTargetFrom());
                rekomTarget.setTargetTo(tReq.getTargetTo());
                rekom.getTarget().add(rekomTarget);

                LOG.info("New Target {}" ,rekomTarget);
            }
        }


        LOG.info("Updated Target : {}" , rekom.getTarget());
        
    }

    @Override
    public Page<RekomListResponse> getRekomPageByFilter(int index, int size, String stockCode, Date startDate,
            Date endDate) {
        Pageable pageable = PageRequest.of(index, size);
        return rekomDao.findRekomPageByFilter(stockCode,startDate,endDate,pageable).map(this::mapRekomToRekomListResponse);
    }


    private RekomListResponse mapRekomToRekomListResponse(Rekom rekom) {

        RekomListResponse data = new RekomListResponse();
        data.setId(rekom.getId());
        data.setCreateBy(rekom.getUser().getUsername());
        data.setStockCode(rekom.getStock().getStockCode());
        data.setEntryFrom(rekom.getEntryFrom());
        data.setEntryTo(rekom.getEntryTo());
        data.setRekomDate(rekom.getRekomDate());
        data.setRekomType(rekom.getRekomType());
        data.setStopLoss(rekom.getStopLoss());


        if(rekom.getTarget().isEmpty()){
            data.setTarget("-");
        }else{
            // create Target format to x - y ,x - y ,n....
            StringBuilder targetSb = new StringBuilder();
            for (RekomTarget target : rekom.getTarget()) {
                targetSb.append(target.getTargetFrom());
                targetSb.append("-");
                targetSb.append(target.getTargetTo());
                targetSb.append(",");
            }
    
            targetSb.deleteCharAt(targetSb.toString().length() - 1);
    
            data.setTarget(targetSb.toString());

        }

        return data;

    }

    
}
