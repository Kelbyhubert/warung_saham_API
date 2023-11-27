package com.warungsaham.warungsahamappapi.payment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.payment.model.Payment;

@Repository
public interface PaymentDao extends JpaRepository<Payment,Integer>{
    
}
