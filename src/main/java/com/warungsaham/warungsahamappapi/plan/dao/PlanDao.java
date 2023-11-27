package com.warungsaham.warungsahamappapi.plan.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.plan.model.Plan;

@Repository
public interface PlanDao extends JpaRepository<Plan,Integer> {
    
}
