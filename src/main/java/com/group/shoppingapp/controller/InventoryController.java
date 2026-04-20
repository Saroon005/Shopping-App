package com.group.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group.shoppingapp.dto.InventoryDTO;
import com.group.shoppingapp.service.InventoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@Valid @RequestBody InventoryDTO dto) {
        InventoryDTO created = inventoryService.createInventory(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        List<InventoryDTO> list = inventoryService.getAllInventory();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getById(@PathVariable Long id) {
        InventoryDTO dto = inventoryService.getById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id,
                                                        @Valid @RequestBody InventoryDTO dto) {
        InventoryDTO updated = inventoryService.updateInventory(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryDTO>> getLowStock() {
        List<InventoryDTO> lowStockList = inventoryService.getLowStock();
        return new ResponseEntity<>(lowStockList, HttpStatus.OK);
    }
}