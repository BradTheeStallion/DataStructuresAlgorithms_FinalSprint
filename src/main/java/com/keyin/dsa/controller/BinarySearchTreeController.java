package com.keyin.dsa.controller;

import com.keyin.dsa.dto.TreeDTO;
import com.keyin.dsa.service.BinarySearchTreeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trees")
@CrossOrigin(origins = "*")
public class BinarySearchTreeController {
    private final BinarySearchTreeService treeService;

    @Autowired
    public BinarySearchTreeController(BinarySearchTreeService treeService) {
        this.treeService = treeService;
    }

    @PostMapping
    public ResponseEntity<TreeDTO.TreeResponse> createTree(@Valid @RequestBody TreeDTO.TreeCreateRequest request) {
        TreeDTO.TreeResponse response = treeService.createTree(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeDTO.TreeResponse> getTree(@PathVariable Long id) {
        TreeDTO.TreeResponse response = treeService.getTree(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<TreeDTO.TreeListResponse> getAllTrees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        TreeDTO.TreeListResponse response = treeService.getAllTrees(page, size);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }
}