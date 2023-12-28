package com.warungsaham.warungsahamappapi.premiumsub.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warungsaham.warungsahamappapi.payment.model.Payment;
import com.warungsaham.warungsahamappapi.plan.model.Plan;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_premium_sub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PremiumSub {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentId", referencedColumnName = "id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "planId", nullable = false)
    private Plan plan;

    private Date endDate;

    private Date startDate;

}
