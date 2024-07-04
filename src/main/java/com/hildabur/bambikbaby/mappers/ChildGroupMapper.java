package com.hildabur.bambikbaby.mappers;

import com.hildabur.bambikbaby.dto.get.ChildGroupDTO;
import com.hildabur.bambikbaby.models.ChildGroup;

public class ChildGroupMapper {
    public static ChildGroupDTO toDTO(ChildGroup childGroup) {
        ChildGroupDTO childGroupDTO = new ChildGroupDTO();
        childGroupDTO.setId(childGroup.getId());
        childGroupDTO.setTitle(childGroup.getTitle());
        childGroupDTO.setChief(UserMapper.toDTO(childGroup.getChief()));
        return childGroupDTO;
    }
}
