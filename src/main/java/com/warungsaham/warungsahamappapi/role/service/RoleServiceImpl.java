package com.warungsaham.warungsahamappapi.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.warungsaham.warungsahamappapi.role.dao.RoleDao;
import com.warungsaham.warungsahamappapi.role.model.Role;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao){
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRole() {
        Sort sortAscById = Sort.by(Sort.Direction.ASC,"id");
        return roleDao.findAll(sortAscById); 
    }
    
}
