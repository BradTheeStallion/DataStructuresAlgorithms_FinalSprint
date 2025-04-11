package com.keyin.dsa.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeModelTest {

    @Test
    void treeNode_CreateAndModify() {
        TreeNode node1 = new TreeNode();
        assertNull(node1.getLeft());
        assertNull(node1.getRight());
        assertEquals(0, node1.getVal());

        TreeNode node2 = new TreeNode(42);
        assertEquals(42, node2.getVal());
        assertTrue(node2.isLeaf());

        TreeNode left = new TreeNode(10);
        TreeNode right = new TreeNode(50);
        TreeNode root = new TreeNode(30, left, right);

        assertEquals(30, root.getVal());
        assertSame(left, root.getLeft());
        assertSame(right, root.getRight());
        assertFalse(root.isLeaf());

        root.setVal(35);
        assertEquals(35, root.getVal());

        TreeNode newLeft = new TreeNode(15);
        root.setLeft(newLeft);
        assertSame(newLeft, root.getLeft());
    }

    @Test
    void treeNode_HeightCalculation() {
        TreeNode leaf = new TreeNode(5);
        assertEquals(0, leaf.height());

        TreeNode simpleRoot = new TreeNode(10);
        simpleRoot.setLeft(new TreeNode(5));
        simpleRoot.setRight(new TreeNode(15));
        assertEquals(1, simpleRoot.height());

        TreeNode complexRoot = new TreeNode(10);
        TreeNode left = new TreeNode(5);
        left.setLeft(new TreeNode(3));
        complexRoot.setLeft(left);
        complexRoot.setRight(new TreeNode(15));
        assertEquals(2, complexRoot.height());
    }

    @Test
    void treeNodeEntity_CreateAndModify() {
        TreeNodeEntity node1 = new TreeNodeEntity();
        assertNull(node1.getId());
        assertNull(node1.getValue());

        TreeNodeEntity node2 = new TreeNodeEntity(42, 1L);
        assertEquals(42, node2.getValue());
        assertEquals(1L, node2.getTreeId());
        assertTrue(node2.isLeaf());

        TreeNodeEntity node3 = new TreeNodeEntity(1L, 30, 2L, 3L, 1L);
        assertEquals(1L, node3.getId());
        assertEquals(30, node3.getValue());
        assertEquals(2L, node3.getLeftChildId());
        assertEquals(3L, node3.getRightChildId());
        assertEquals(1L, node3.getTreeId());
        assertFalse(node3.isLeaf());

        node3.setValue(35);
        assertEquals(35, node3.getValue());

        node3.setLeftChildId(5L);
        assertEquals(5L, node3.getLeftChildId());
    }

    @Test
    void binarySearchTree_CreateAndModify() {
        BinarySearchTree bst1 = new BinarySearchTree();
        assertNotNull(bst1.getCreatedAt());
        assertNotNull(bst1.getOriginalInputs());
        assertTrue(bst1.getOriginalInputs().isEmpty());

        List<Integer> inputs = Arrays.asList(5, 3, 7, 2, 4, 6, 8);
        BinarySearchTree bst2 = new BinarySearchTree(inputs);
        assertNotNull(bst2.getName());
        assertTrue(bst2.getName().startsWith("BST_"));
        assertEquals(inputs, bst2.getOriginalInputs());

        BinarySearchTree bst3 = new BinarySearchTree("Custom Tree", inputs);
        assertEquals("Custom Tree", bst3.getName());
        assertEquals(inputs, bst3.getOriginalInputs());

        LocalDateTime now = LocalDateTime.now();
        BinarySearchTree bst4 = new BinarySearchTree(
                1L, "Test Tree", now, 100L, inputs, 7, 2, true
        );

        assertEquals(1L, bst4.getId());
        assertEquals("Test Tree", bst4.getName());
        assertEquals(now, bst4.getCreatedAt());
        assertEquals(100L, bst4.getRootNodeId());
        assertEquals(inputs, bst4.getOriginalInputs());
        assertEquals(7, bst4.getNodeCount());
        assertEquals(2, bst4.getHeight());
        assertTrue(bst4.getIsBalanced());

        bst4.setName("Updated Tree");
        assertEquals("Updated Tree", bst4.getName());

        bst4.setIsBalanced(false);
        assertFalse(bst4.getIsBalanced());
    }

    @Test
    void binarySearchTree_FormattingAndJson() {
        List<Integer> inputs = Arrays.asList(5, 3, 7);
        BinarySearchTree bst = new BinarySearchTree(inputs);
        bst.setId(1L);
        bst.setNodeCount(3);
        bst.setHeight(1);
        bst.setIsBalanced(true);

        assertNotNull(bst.getFormattedCreationDate());
        assertTrue(bst.getFormattedCreationDate().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));

        String json = bst.toJson();
        assertNotNull(json);
        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"originalInputs\":[5,3,7]"));
        assertTrue(json.contains("\"nodeCount\":3"));
    }
}