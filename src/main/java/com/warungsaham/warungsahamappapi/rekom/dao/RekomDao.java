package com.warungsaham.warungsahamappapi.rekom.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.rekom.model.Rekom;

@Repository
public interface RekomDao extends JpaRepository<Rekom,Integer> {
    
        @Query(
        value = "select i from Rekom i \r\n" + //
                        "where (:stockCode IS NULL or '' = :stockCode or i.stock.stockCode like :stockCode%)\r\n" + //
                        "and (:startDate IS NULL or i.rekomDate >= :startDate)\r\n" + //
                        "and (:endDate IS NULL or i.rekomDate <= :endDate)\r\n" +
                        "order by i.rekomDate Desc"
    )
    Page<Rekom> findRekomPageByFilter(@Param("stockCode") String stockCode,
                                            @Param("startDate") Date startDate, 
                                            @Param("endDate") Date endDate,
                                            Pageable pageable);

}
