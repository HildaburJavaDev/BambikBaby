package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.ChildGroupRepository;
import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.dto.get.ChildGroupDTO;
import com.hildabur.bambikbaby.mappers.ChildGroupMapper;
import com.hildabur.bambikbaby.models.ChildGroup;
import com.hildabur.bambikbaby.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChildGroupService {
    private ChildGroupRepository childGroupRepository;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public ChildGroupDTO createGroup(ChildGroup childGroup, int chiefId) {
        User user = userRepository.findById(chiefId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        if (user.getUserRole().getName().equals("admin") || user.getUserRole().getName().equals("employee")) {
            childGroup.setChief(user);
            childGroupRepository.save(childGroup);
            return ChildGroupMapper.toDTO(childGroup);
        } else {
            throw new IllegalArgumentException("Нельзя назначить простого пользователя воспитателем в группе");
        }

    }
}
