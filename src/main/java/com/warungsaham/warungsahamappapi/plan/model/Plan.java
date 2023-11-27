package com.warungsaham.warungsahamappapi.plan.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warungsaham.warungsahamappapi.premiumsub.model.PremiumSub;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_plan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String planName;

    private int duration;

    private String desc;

    @JsonIgnore
    @OneToMany(mappedBy = "plan")
    private List<PremiumSub> premiumSubList = new ArrayList<>();

}
