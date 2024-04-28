package com.warungsaham.warungsahamappapi.insight.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.insight.model.Insight;

@Repository
public interface InsightDao extends JpaRepository<Insight,Integer> {
    
    @Query(
        value = "select i from Insight i \r\n" + //
                        "where (:title IS NULL or '' = :title or i.title like :title%)\r\n" + //
                        "and (:createBy IS NULL or '' = :createBy or i.createBy = :createBy)\r\n" + //
                        "and (:startDate IS NULL or i.createDate >= :startDate)\r\n" + //
                        "and (:endDate IS NULL or i.createDate <= :endDate)\r\n" +
                        "order by i.createDate Desc"
    )
    Page<Insight> findInsightPageByFilter(@Param("title") String title,
                                            @Param("createBy") String createBy, 
                                            @Param("startDate") Date startDate, 
                                            @Param("endDate") Date endDate,
                                            Pageable pageable);
}
