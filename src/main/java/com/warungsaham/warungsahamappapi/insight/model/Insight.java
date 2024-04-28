package com.warungsaham.warungsahamappapi.insight.model;

import java.util.Date;



import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_insight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Insight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",nullable = true)
    private User user;

    private String title;

    private String thumbnailImg;

    private String content;

    private String createBy;

    private String updateBy;

    private Date createDate;

    private Date updateDate;
    
}
