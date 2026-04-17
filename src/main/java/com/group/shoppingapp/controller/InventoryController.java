package com.group.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group.shoppingapp.dto.InventoryDTO;
import com.group.shoppingapp.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public InventoryDTO createInventory(@RequestBody InventoryDTO dto) {
        return inventoryService.createInventory(dto);
    }

    @GetMapping
    public List<InventoryDTO> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public InventoryDTO getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @PutMapping("/{id}")
    public InventoryDTO updateInventory(@PathVariable Long id,
                                        @RequestBody InventoryDTO dto) {
        return inventoryService.updateInventory(id, dto);
    }

    @GetMapping("/low-stock")
    public List<InventoryDTO> getLowStock() {
        return inventoryService.getLowStock();
    }
}