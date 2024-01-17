package com.warungsaham.warungsahamappapi.stock.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_saham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    

    @Id
    @Column(name = "kode_saham")
    private String stockCode;

    @Column(name = "perusahaan")
    private String company;

    @Column(name = "sektor")
    private String sector;



}
