package com.keyin.dsa.service;

import com.keyin.dsa.dto.TreeDTO;
import com.keyin.dsa.model.BinarySearchTree;
import com.keyin.dsa.model.TreeNodeEntity;
import com.keyin.dsa.repository.BinarySearchTreeRepository;
import com.keyin.dsa.repository.TreeNodeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BinarySearchTreeServiceTest {

    @Mock
    private BinarySearchTreeRepository bstRepository;

    @Mock
    private TreeNodeRepository nodeRepository;

    @InjectMocks
    private BinarySearchTreeService treeService;

    private BinarySearchTree testTree;
    private TreeNodeEntity rootNode;
    private TreeNodeEntity leftNode;
    private TreeNodeEntity rightNode;
    private List<Integer> testValues;

    @BeforeEach
    void setUp() {
        testValues = Arrays.asList(50, 30, 70, 20, 40, 60, 80);

        testTree = new BinarySearchTree();
        testTree.setId(1L);
        testTree.setName("Test Tree");
        testTree.setCreatedAt(LocalDateTime.now());
        testTree.setOriginalInputs(testValues);
        testTree.setNodeCount(7);
        testTree.setHeight(2);
        testTree.setIsBalanced(true);
        testTree.setRootNodeId(1L);

        rootNode = new TreeNodeEntity(1L, 50, 2L, 3L, 1L);
        leftNode = new TreeNodeEntity(2L, 30, 4L, 5L, 1L);
        rightNode = new TreeNodeEntity(3L, 70, 6L, 7L, 1L);
    }

    @Test
    void createTree_ShouldCreateAndPersistTree() {
        TreeDTO.TreeCreateRequest request = new TreeDTO.TreeCreateRequest(testValues, "Test Tree");

        when(bstRepository.save(any(BinarySearchTree.class))).thenAnswer(invocation -> {
            BinarySearchTree savedTree = invocation.getArgument(0);
            savedTree.setId(1L);
            return savedTree;
        });

        when(nodeRepository.save(any(TreeNodeEntity.class))).thenAnswer(invocation -> {
            TreeNodeEntity node = invocation.getArgument(0);
            if (node.getId() == null) {
                node.setId(1L + (long)(Math.random() * 100));
            }
            return node;
        });

        TreeDTO.TreeResponse response = treeService.createTree(request);

        assertNotNull(response);
        assertEquals("Test Tree", response.getName());
        assertEquals(testValues, response.getOriginalInputs());
        assertNotNull(response.getNodeCount());
        assertNotNull(response.getHeight());
        assertNotNull(response.getIsBalanced());

        verify(bstRepository, times(2)).save(any(BinarySearchTree.class));
        verify(nodeRepository, atLeast(testValues.size())).save(any(TreeNodeEntity.class));
    }

    @Test
    void createTree_WithEmptyValues_ShouldThrowException() {
        TreeDTO.TreeCreateRequest request = new TreeDTO.TreeCreateRequest(List.of(), "Empty Tree");

        assertThrows(IllegalArgumentException.class, () -> treeService.createTree(request));
        verify(bstRepository, never()).save(any(BinarySearchTree.class));
    }

    @Test
    void getTree_WithExistingId_ShouldReturnTree() {
        Long treeId = 1L;
        List<TreeNodeEntity> allNodes = Arrays.asList(rootNode, leftNode, rightNode);

        when(bstRepository.findById(treeId)).thenReturn(Optional.of(testTree));
        when(nodeRepository.findByTreeId(treeId)).thenReturn(allNodes);

        TreeDTO.TreeResponse response = treeService.getTree(treeId);

        assertNotNull(response);
        assertEquals(treeId, response.getId());
        assertEquals("Test Tree", response.getName());
        assertEquals(testValues, response.getOriginalInputs());
        assertEquals(7, response.getNodeCount());
        assertEquals(2, response.getHeight());
        assertTrue(response.getIsBalanced());
    }

    @Test
    void getTree_WithNonExistingId_ShouldThrowException() {
        Long treeId = 999L;
        when(bstRepository.findById(treeId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> treeService.getTree(treeId));
    }

    @Test
    void getAllTrees_ShouldReturnPaginatedList() {
        int page = 0;
        int size = 10;
        List<BinarySearchTree> treeList = List.of(testTree);
        Page<BinarySearchTree> treePage = new PageImpl<>(treeList);

        when(bstRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        )).thenReturn(treePage);

        TreeDTO.TreeListResponse response = treeService.getAllTrees(page, size);

        assertNotNull(response);
        assertEquals(1, response.getTrees().size());
        assertEquals(1, response.getTotalCount());
        assertEquals(page, response.getPage());
        assertEquals(size, response.getSize());
    }

    @Test
    void deleteTree_WithExistingId_ShouldDeleteTree() {
        Long treeId = 1L;
        when(bstRepository.existsById(treeId)).thenReturn(true);
        doNothing().when(nodeRepository).deleteByTreeId(treeId);
        doNothing().when(bstRepository).deleteById(treeId);

        treeService.deleteTree(treeId);

        verify(nodeRepository).deleteByTreeId(treeId);
        verify(bstRepository).deleteById(treeId);
    }

    @Test
    void deleteTree_WithNonExistingId_ShouldThrowException() {
        Long treeId = 999L;
        when(bstRepository.existsById(treeId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> treeService.deleteTree(treeId));
        verify(nodeRepository, never()).deleteByTreeId(anyLong());
        verify(bstRepository, never()).deleteById(anyLong());
    }
}