package com.warungsaham.warungsahamappapi.plan.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.warungsaham.warungsahamappapi.plan.dao.PlanDao;
import com.warungsaham.warungsahamappapi.plan.model.Plan;

@Service
public class PlanServiceImpl implements PlanService {

    private PlanDao planDao;

    public PlanServiceImpl(PlanDao planDao){
        this.planDao = planDao;
    }

    @Override
    public List<Plan> getAllPlan() {
        return planDao.findAll();
        
    }
    
}
