package com.keyin.dsa.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "binary_search_trees")
public class BinarySearchTree implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Long rootNodeId;

    @ElementCollection
    @CollectionTable(name = "bst_original_inputs", joinColumns = @JoinColumn(name = "bst_id"))
    @Column(name = "input_value")
    @OrderColumn(name = "sequence")
    private List<Integer> originalInputs;

    @Column(name = "node_count")
    private Integer nodeCount;

    private Integer height;

    @Column(name = "is_balanced")
    private Boolean isBalanced;

    @Transient
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public BinarySearchTree() {
        this.createdAt = LocalDateTime.now();
        this.originalInputs = new ArrayList<>();
    }

    public BinarySearchTree(List<Integer> inputs) {
        this();
        this.originalInputs = new ArrayList<>(inputs);
        this.name = "BST_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    public BinarySearchTree(String name, List<Integer> inputs) {
        this();
        this.name = name;
        this.originalInputs = new ArrayList<>(inputs);
    }

    public BinarySearchTree(Long id, String name, LocalDateTime createdAt,
                            Long rootNodeId, List<Integer> originalInputs,
                            Integer nodeCount, Integer height, Boolean isBalanced) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.rootNodeId = rootNodeId;
        this.originalInputs = originalInputs;
        this.nodeCount = nodeCount;
        this.height = height;
        this.isBalanced = isBalanced;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRootNodeId() {
        return rootNodeId;
    }

    public void setRootNodeId(Long rootNodeId) {
        this.rootNodeId = rootNodeId;
    }

    public List<Integer> getOriginalInputs() {
        return originalInputs;
    }

    public void setOriginalInputs(List<Integer> originalInputs) {
        this.originalInputs = originalInputs;
    }

    public Integer getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getIsBalanced() {
        return isBalanced;
    }

    public void setIsBalanced(Boolean isBalanced) {
        this.isBalanced = isBalanced;
    }

    public String getFormattedCreationDate() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "BinarySearchTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", rootNodeId=" + rootNodeId +
                ", originalInputs=" + originalInputs +
                ", nodeCount=" + nodeCount +
                ", height=" + height +
                ", isBalanced=" + isBalanced +
                '}';
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinarySearchTree that = (BinarySearchTree) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(rootNodeId, that.rootNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, rootNodeId);
    }
}