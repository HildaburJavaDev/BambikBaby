package com.hildabur.bambikbaby.dao;

import com.hildabur.bambikbaby.models.ChildGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChildGroupRepository extends JpaRepository<ChildGroup, Integer> {
    List<ChildGroup> findAll();
    @Query(value = "SELECT * FROM child_groups cg JOIN users u ON u.id = cg.chief_id WHERE cg.chief_id = :leaderId", nativeQuery = true)
    List<ChildGroup> findByChiefIdWithUserNative(int leaderId);
}
