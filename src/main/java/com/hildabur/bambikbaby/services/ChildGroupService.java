package com.hildabur.bambikbaby.services;

import com.hildabur.bambikbaby.dao.ChildGroupRepository;
import com.hildabur.bambikbaby.dao.UserRepository;
import com.hildabur.bambikbaby.dto.ChildGroupDTO;
import com.hildabur.bambikbaby.mappers.ChildGroupMapper;
import com.hildabur.bambikbaby.models.ChildGroup;
import com.hildabur.bambikbaby.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    private List<ChildGroup> findAllForEmployee(int leaderId) {
        return childGroupRepository.findByChiefIdWithUserNative(leaderId);
    }

    public List<ChildGroup> findAll(UserDetailsImpl userDetails) {
        if (userDetails.getRoleName().equals("admin")) {
            return findAll();
        } else {
            return findAllForEmployee(userDetails.getId().intValue());
        }
    }

    public ChildGroupDTO createGroup(ChildGroup childGroup, int leaderId) {
        User user = userRepository.findById(leaderId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        if (checkUserLeaderPermission(user)) {
            childGroup.setChief(user);
            childGroupRepository.save(childGroup);
            return ChildGroupMapper.toDTO(childGroup);
        } else {
            throw new IllegalArgumentException("Нельзя назначить простого пользователя воспитателем в группе");
        }
    }
    private boolean checkUserLeaderPermission(User user) {
        return user.getUserRole().getName().equals("admin") || user.getUserRole().getName().equals("employee");
    }
    public void updateGroupLeader(int leaderId, int groupId) {
        User user = userRepository.findById(leaderId)
                .orElseThrow(()-> new IllegalArgumentException("Пользователь не найден"));
        if (checkUserLeaderPermission(user)) {
            ChildGroup childGroup = childGroupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Группа не найдена"));
            childGroup.setChief(user);
            childGroupRepository.save(childGroup);
        }
    }
}
