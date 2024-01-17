package com.warungsaham.warungsahamappapi.rekom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.rekom.model.Rekom;

@Repository
public interface RekomDao extends JpaRepository<Rekom,Integer> {
    
}
