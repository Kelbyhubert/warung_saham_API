package com.warungsaham.warungsahamappapi.payment.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warungsaham.warungsahamappapi.premiumsub.model.PremiumSub;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String paymentType;

    private String filename;

    private String dirPath;

    private String originalName;

    private String fileType;

    private Date paymentDate;

    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    private PremiumSub premiumSubList;

}
