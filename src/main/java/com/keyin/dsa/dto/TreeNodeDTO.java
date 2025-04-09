package com.keyin.dsa.dto;

public class TreeNodeDTO {
    private Long id;
    private Integer value;
    private TreeNodeDTO left;
    private TreeNodeDTO right;

    public TreeNodeDTO() {}

    public TreeNodeDTO(Long id, Integer value) {
        this.id = id;
        this.value = value;
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

    public TreeNodeDTO getLeft() {
        return left;
    }

    public void setLeft(TreeNodeDTO left) {
        this.left = left;
    }

    public TreeNodeDTO getRight() {
        return right;
    }

    public void setRight(TreeNodeDTO right) {
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}