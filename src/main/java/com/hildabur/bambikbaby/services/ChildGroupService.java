package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.ChildGroupRepository;
import com.hildabur.bambikbaby.models.ChildGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChildGroupService {
    private ChildGroupRepository childGroupRepository;

    @Autowired
    public void setChildGroupRepository(ChildGroupRepository childGroupRepository) {
        this.childGroupRepository = childGroupRepository;
    }

    private List<ChildGroup> findAll() {
       return childGroupRepository.findAll();
    }

    private List<ChildGroup> findAllForEmployee(int chiefId) {
        return childGroupRepository.findByChiefIdWithUserNative(chiefId);
    }

    public List<ChildGroup> findAll(UserDetailsImpl userDetails) {
        if (userDetails.getRoleName().equals("admin")) {
            return findAll();
        } else {
            return findAllForEmployee(userDetails.getId().intValue());
        }
    }
}
