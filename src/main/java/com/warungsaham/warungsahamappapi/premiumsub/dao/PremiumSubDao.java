package com.warungsaham.warungsahamappapi.premiumsub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.premiumsub.model.PremiumSub;

@Repository
public interface PremiumSubDao extends JpaRepository<PremiumSub,Integer> {
    List<PremiumSub> findAllByUserId(int id);
}
