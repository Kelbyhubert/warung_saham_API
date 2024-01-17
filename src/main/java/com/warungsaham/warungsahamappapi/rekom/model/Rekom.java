package com.warungsaham.warungsahamappapi.rekom.model;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warungsaham.warungsahamappapi.stock.model.Stock;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_rekom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rekom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kodeSaham", nullable = false)
    private Stock stock;

    @Column(name = "kodeSaham" ,insertable=false, updatable=false)
    private String kodeSaham;

    @OneToMany(mappedBy = "rekom" ,cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<RekomTarget> target = new ArrayList<>();

    @Column(name = "dekripsi")
    private String description;

    private Date rekomDate;
    private int entryFrom;
    private int entryTo;
    private int stopLoss;
    private String rekomType;

}
