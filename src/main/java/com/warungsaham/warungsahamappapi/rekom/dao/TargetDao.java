package com.warungsaham.warungsahamappapi.rekom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.rekom.model.RekomTarget;

@Repository
public interface TargetDao extends JpaRepository<RekomTarget,Integer> {
    
}
