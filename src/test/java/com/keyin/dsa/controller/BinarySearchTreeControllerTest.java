package com.keyin.dsa.controller;

import com.keyin.dsa.dto.TreeDTO;
import com.keyin.dsa.service.BinarySearchTreeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BinarySearchTreeControllerTest {

    private BinarySearchTreeService mockService;
    private BinarySearchTreeController controller;

    @BeforeEach
    void setUp() {
        mockService = mock(BinarySearchTreeService.class);
        controller = new BinarySearchTreeController(mockService);
    }

    @Test
    void testCreateTree() {
        TreeDTO.TreeCreateRequest request = new TreeDTO.TreeCreateRequest(
                Arrays.asList(10, 5, 15), "Test Tree"
        );

        TreeDTO.TreeResponse responseMock = new TreeDTO.TreeResponse();
        responseMock.setId(1L);
        responseMock.setName("Test Tree");

        when(mockService.createTree(request)).thenReturn(responseMock);

        ResponseEntity<TreeDTO.TreeResponse> response = controller.createTree(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test Tree", response.getBody().getName());
    }

    @Test
    void testGetTree() {
        Long treeId = 1L;
        TreeDTO.TreeResponse responseMock = new TreeDTO.TreeResponse();
        responseMock.setId(treeId);
        responseMock.setName("Test Tree");

        when(mockService.getTree(treeId)).thenReturn(responseMock);

        ResponseEntity<TreeDTO.TreeResponse> response = controller.getTree(treeId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(treeId, response.getBody().getId());
    }

    @Test
    void testGetAllTrees() {
        TreeDTO.TreeListResponse mockList = new TreeDTO.TreeListResponse();

        TreeDTO.TreeSummary t1 = new TreeDTO.TreeSummary();
        t1.setId(1L);
        t1.setName("Tree A");
        t1.setCreatedAt(LocalDateTime.now());
        t1.setHeight(3);
        t1.setNodeCount(5);
        t1.setIsBalanced(true);

        TreeDTO.TreeSummary t2 = new TreeDTO.TreeSummary();
        t2.setId(2L);
        t2.setName("Tree B");
        t2.setCreatedAt(LocalDateTime.now());
        t2.setHeight(4);
        t2.setNodeCount(7);
        t2.setIsBalanced(false);

        mockList.setTrees(Arrays.asList(t1, t2));
        mockList.setTotalCount(2L);
        mockList.setPage(0);
        mockList.setSize(10);

        when(mockService.getAllTrees(0, 10)).thenReturn(mockList);

        ResponseEntity<TreeDTO.TreeListResponse> response = controller.getAllTrees(0, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getTrees().size());
        assertEquals("Tree A", response.getBody().getTrees().get(0).getName());
    }

    @Test
    void testDeleteTree() {
        Long treeId = 1L;

        doNothing().when(mockService).deleteTree(treeId);

        ResponseEntity<Void> response = controller.deleteTree(treeId);

        assertEquals(204, response.getStatusCodeValue());
        verify(mockService, times(1)).deleteTree(treeId);
    }
}