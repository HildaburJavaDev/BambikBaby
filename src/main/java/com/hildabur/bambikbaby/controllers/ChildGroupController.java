package com.hildabur.bambikbaby.controllers;

import com.hildabur.bambikbaby.dto.ChildGroupDTO;
import com.hildabur.bambikbaby.dto.ChildGroupPostDTO;
import com.hildabur.bambikbaby.dto.GroupLeaderDTO;
import com.hildabur.bambikbaby.mappers.ChildGroupMapper;
import com.hildabur.bambikbaby.models.ChildGroup;
import com.hildabur.bambikbaby.services.ChildGroupService;
import com.hildabur.bambikbaby.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
public class ChildGroupController {
    private ChildGroupService childGroupService;

    @Autowired
    public void setChildGroupService(ChildGroupService childGroupService) {
        this.childGroupService = childGroupService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getChildGroups(Authentication authentication) {
        List<ChildGroup> childGroups = childGroupService.findAll((UserDetailsImpl) authentication.getPrincipal());
        if (childGroups.isEmpty()) {
            return ResponseEntity.ok("Нет доступных групп");
        }
        List<ChildGroupDTO> childGroupDTOs = childGroups.stream()
                                            .map(ChildGroupMapper::toDTO)
                                            .collect(Collectors.toList());
        return ResponseEntity.ok(childGroupDTOs);
    }
    @PostMapping("/")
    public ResponseEntity<?> createChildGroup(@RequestBody ChildGroupPostDTO childGroupPostDTO) {
        ChildGroup childGroup = ChildGroupMapper.toEntity(childGroupPostDTO);
        try {
            ChildGroupDTO childGroupDTO = childGroupService.createGroup(childGroup, childGroupPostDTO.getChiefId());
            return ResponseEntity.ok(childGroupDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }
    @PatchMapping("/")
    public ResponseEntity<?> updateGroupChief(@RequestBody GroupLeaderDTO groupLeaderDTO) {

        return ResponseEntity.ok("Пользователь назначен в группу");
    }
}
