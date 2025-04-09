package com.keyin.dsa.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class TreeDTO {
    public static class TreeCreateRequest {
        private List<Integer> values;
        private String name;

        public TreeCreateRequest() {
            this.values = new ArrayList<>();
        }

        public TreeCreateRequest(List<Integer> values, String name) {
            this.values = values;
            this.name = name;
        }

        public List<Integer> getValues() {
            return values;
        }

        public void setValues(List<Integer> values) {
            this.values = values;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class TreeResponse {
        private Long id;
        private String name;
        private LocalDateTime createdAt;
        private List<Integer> originalInputs;
        private Integer nodeCount;
        private Integer height;
        private Boolean isBalanced;
        private TreeNodeDTO rootNode;

        public TreeResponse() {}

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

        public TreeNodeDTO getRootNode() {
            return rootNode;
        }

        public void setRootNode(TreeNodeDTO rootNode) {
            this.rootNode = rootNode;
        }
    }

    public static class TreeListResponse {
        private List<TreeSummary> trees;
        private long totalCount;
        private int page;
        private int size;

        public TreeListResponse() {
            this.trees = new ArrayList<>();
        }

        public List<TreeSummary> getTrees() {
            return trees;
        }

        public void setTrees(List<TreeSummary> trees) {
            this.trees = trees;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class TreeSummary {
        private Long id;
        private String name;
        private LocalDateTime createdAt;
        private Integer nodeCount;
        private Integer height;
        private Boolean isBalanced;

        public TreeSummary() {}

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
    }
}