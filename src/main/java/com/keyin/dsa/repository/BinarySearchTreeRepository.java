package com.keyin.dsa.repository;

import com.keyin.dsa.model.BinarySearchTree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BinarySearchTreeRepository extends JpaRepository<BinarySearchTree, Long> {
    List<BinarySearchTree> findByNameContainingIgnoreCase(String name);

    Page<BinarySearchTree> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<BinarySearchTree> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}