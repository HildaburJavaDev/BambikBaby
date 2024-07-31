package com.hildabur.bambikbaby.mappers;

import com.hildabur.bambikbaby.dto.ChildGroupDTO;
import com.hildabur.bambikbaby.dto.ChildGroupPostDTO;
import com.hildabur.bambikbaby.models.ChildGroup;

public class ChildGroupMapper {
    public static ChildGroupDTO toDTO(ChildGroup childGroup) {
        ChildGroupDTO childGroupDTO = new ChildGroupDTO();
        childGroupDTO.setId(childGroup.getId());
        childGroupDTO.setTitle(childGroup.getTitle());
        childGroupDTO.setChief(UserMapper.toDTO(childGroup.getChief()));
        return childGroupDTO;
    }

    public static ChildGroup toEntity(ChildGroupPostDTO childGroupPostDTO) {
        ChildGroup childGroup = new ChildGroup();
        childGroup.setTitle(childGroupPostDTO.getTitle());
        return childGroup;
    }
}
