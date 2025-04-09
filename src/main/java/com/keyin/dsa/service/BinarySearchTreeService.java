package com.keyin.dsa.service;

import com.keyin.dsa.dto.TreeDTO;
import com.keyin.dsa.dto.TreeNodeDTO;
import com.keyin.dsa.model.BinarySearchTree;
import com.keyin.dsa.model.TreeNode;
import com.keyin.dsa.model.TreeNodeEntity;
import com.keyin.dsa.repository.BinarySearchTreeRepository;
import com.keyin.dsa.repository.TreeNodeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BinarySearchTreeService {
    private final BinarySearchTreeRepository bstRepository;
    private final TreeNodeRepository nodeRepository;

    @Autowired
    public BinarySearchTreeService(BinarySearchTreeRepository bstRepository, TreeNodeRepository nodeRepository) {
        this.bstRepository = bstRepository;
        this.nodeRepository = nodeRepository;
    }

    @Transactional
    public TreeDTO.TreeResponse createTree(TreeDTO.TreeCreateRequest request) {
        if (request.getValues() == null || request.getValues().isEmpty()) {
            throw new IllegalArgumentException("Input values cannot be empty");
        }

        BinarySearchTree bst = new BinarySearchTree();
        bst.setName(request.getName() != null ? request.getName() :
                "BST_" + System.currentTimeMillis());
        bst.setOriginalInputs(new ArrayList<>(request.getValues()));
        bst = bstRepository.save(bst);

        TreeNode rootNode = buildBST(request.getValues());

        int nodeCount = countNodes(rootNode);
        int height = getHeight(rootNode);
        boolean isBalanced = checkBalanced(rootNode);

        bst.setNodeCount(nodeCount);
        bst.setHeight(height);
        bst.setIsBalanced(isBalanced);

        Long rootNodeId = persistTreeStructure(rootNode, bst.getId());
        bst.setRootNodeId(rootNodeId);
        bst = bstRepository.save(bst);

        return buildTreeResponse(bst);
    }

    @Transactional(readOnly = true)
    public TreeDTO.TreeResponse getTree(Long id) {
        BinarySearchTree bst = bstRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found with id: " + id));

        return buildTreeResponse(bst);
    }

    @Transactional(readOnly = true)
    public TreeDTO.TreeListResponse getAllTrees(int page, int size) {
        Page<BinarySearchTree> trees = bstRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );

        TreeDTO.TreeListResponse response = new TreeDTO.TreeListResponse();
        response.setTrees(trees.getContent().stream()
                .map(this::convertToSummary)
                .collect(Collectors.toList()));
        response.setTotalCount(trees.getTotalElements());
        response.setPage(page);
        response.setSize(size);

        return response;
    }

    @Transactional
    public void deleteTree(Long id) {
        if (!bstRepository.existsById(id)) {
            throw new EntityNotFoundException("Tree not found with id: " + id);
        }

        nodeRepository.deleteByTreeId(id);
        bstRepository.deleteById(id);
    }

    private TreeNode buildBST(List<Integer> values) {
        TreeNode root = null;
        for (Integer val : values) {
            root = insert(root, val);
        }
        return root;
    }

    private TreeNode insert(TreeNode root, int value) {
        if (root == null) {
            return new TreeNode(value);
        }

        if (value < root.getVal()) {
            root.setLeft(insert(root.getLeft(), value));
        } else if (value > root.getVal()) {
            root.setRight(insert(root.getRight(), value));
        }

        return root;
    }

    private int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
    }

    private boolean checkBalanced(TreeNode node) {
        if (node == null) {
            return true;
        }

        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());

        if (Math.abs(leftHeight - rightHeight) <= 1
                && checkBalanced(node.getLeft())
                && checkBalanced(node.getRight())) {
            return true;
        }

        return false;
    }

    private Long persistTreeStructure(TreeNode node, Long treeId) {
        if (node == null) {
            return null;
        }

        TreeNodeEntity entity = new TreeNodeEntity();
        entity.setValue(node.getVal());
        entity.setTreeId(treeId);
        entity = nodeRepository.save(entity);

        Long nodeId = entity.getId();

        Long leftId = persistTreeStructure(node.getLeft(), treeId);
        Long rightId = persistTreeStructure(node.getRight(), treeId);

        entity.setLeftChildId(leftId);
        entity.setRightChildId(rightId);
        nodeRepository.save(entity);

        return nodeId;
    }

    private TreeDTO.TreeResponse buildTreeResponse(BinarySearchTree bst) {
        TreeDTO.TreeResponse response = new TreeDTO.TreeResponse();
        response.setId(bst.getId());
        response.setName(bst.getName());
        response.setCreatedAt(bst.getCreatedAt());
        response.setOriginalInputs(bst.getOriginalInputs());
        response.setNodeCount(bst.getNodeCount());
        response.setHeight(bst.getHeight());
        response.setIsBalanced(bst.getIsBalanced());

        if (bst.getRootNodeId() != null) {
            List<TreeNodeEntity> allNodes = nodeRepository.findByTreeId(bst.getId());
            Map<Long, TreeNodeEntity> nodeMap = new HashMap<>();

            for (TreeNodeEntity node : allNodes) {
                nodeMap.put(node.getId(), node);
            }

            TreeNodeEntity rootEntity = nodeMap.get(bst.getRootNodeId());
            if (rootEntity != null) {
                response.setRootNode(buildTreeNodeDTO(rootEntity, nodeMap));
            }
        }

        return response;
    }

    private TreeNodeDTO buildTreeNodeDTO(TreeNodeEntity entity, Map<Long, TreeNodeEntity> nodeMap) {
        if (entity == null) {
            return null;
        }

        TreeNodeDTO dto = new TreeNodeDTO(entity.getId(), entity.getValue());

        if (entity.getLeftChildId() != null) {
            dto.setLeft(buildTreeNodeDTO(nodeMap.get(entity.getLeftChildId()), nodeMap));
        }

        if (entity.getRightChildId() != null) {
            dto.setRight(buildTreeNodeDTO(nodeMap.get(entity.getRightChildId()), nodeMap));
        }

        return dto;
    }

    private TreeDTO.TreeSummary convertToSummary(BinarySearchTree bst) {
        TreeDTO.TreeSummary summary = new TreeDTO.TreeSummary();
        summary.setId(bst.getId());
        summary.setName(bst.getName());
        summary.setCreatedAt(bst.getCreatedAt());
        summary.setNodeCount(bst.getNodeCount());
        summary.setHeight(bst.getHeight());
        summary.setIsBalanced(bst.getIsBalanced());
        return summary;
    }
}