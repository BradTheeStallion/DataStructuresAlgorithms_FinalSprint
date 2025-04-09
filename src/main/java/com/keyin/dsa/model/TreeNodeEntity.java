package com.keyin.dsa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "tree_nodes")
public class TreeNodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer value;

    private Long leftChildId;

    private Long rightChildId;

    @Column(nullable = false)
    private Long treeId;

    public TreeNodeEntity() {
    }

    public TreeNodeEntity(Integer value, Long treeId) {
        this.value = value;
        this.treeId = treeId;
    }

    public TreeNodeEntity(Long id, Integer value, Long leftChildId, Long rightChildId, Long treeId) {
        this.id = id;
        this.value = value;
        this.leftChildId = leftChildId;
        this.rightChildId = rightChildId;
        this.treeId = treeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getLeftChildId() {
        return leftChildId;
    }

    public void setLeftChildId(Long leftChildId) {
        this.leftChildId = leftChildId;
    }

    public Long getRightChildId() {
        return rightChildId;
    }

    public void setRightChildId(Long rightChildId) {
        this.rightChildId = rightChildId;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public boolean isLeaf() {
        return leftChildId == null && rightChildId == null;
    }

    @Override
    public String toString() {
        return "TreeNodeEntity{" +
                "id=" + id +
                ", value=" + value +
                ", leftChildId=" + leftChildId +
                ", rightChildId=" + rightChildId +
                ", treeId=" + treeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNodeEntity that = (TreeNodeEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value) &&
                Objects.equals(leftChildId, that.leftChildId) &&
                Objects.equals(rightChildId, that.rightChildId) &&
                Objects.equals(treeId, that.treeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, leftChildId, rightChildId, treeId);
    }
}