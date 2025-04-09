package com.keyin.dsa.repository;

import com.keyin.dsa.model.TreeNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeNodeRepository extends JpaRepository<TreeNodeEntity, Long> {
    List<TreeNodeEntity> findByTreeId(Long treeId);

    @Query("SELECT t FROM TreeNodeEntity t WHERE t.id = :nodeId AND t.treeId = :treeId")
    TreeNodeEntity findByIdAndTreeId(@Param("nodeId") Long nodeId, @Param("treeId") Long treeId);

    void deleteByTreeId(Long treeId);
}