package com.warungsaham.warungsahamappapi.role.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.role.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role,Integer> {
    

}
