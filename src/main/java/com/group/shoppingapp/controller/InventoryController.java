package com.group.shoppingapp.controller;

package com.group.shoppingapp.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.group.shoppingapp.dto.InventoryDTO;
import com.group.shoppingapp.entity.Inventory;
import com.group.shoppingapp.repository.InventoryRepository;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostMapping
    public InventoryDTO createInventory(@RequestBody InventoryDTO dto) {

        Inventory inventory = new Inventory();
        inventory.setStockQuantity(dto.getAvailableQuantity());
        inventory.setThreshold(dto.getReorderLevel());
        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory saved = inventoryRepository.save(inventory);

        return mapToDTO(saved);
    }
    
    @GetMapping
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{productId}")
    public InventoryDTO getByProductId(@PathVariable Long productId) {
        Inventory inventory = inventoryRepository.findById(productId)
        		.orElseThrow(() -> new RuntimeException("Id not found"));

        if (inventory == null) {
            throw new RuntimeException("Inventory not found");
        }

        return mapToDTO(inventory);
    }

    @PutMapping("/{productId}")
    public InventoryDTO updateInventory(
            @PathVariable Long productId,
            @RequestBody InventoryDTO dto) {

    	Inventory inventory = inventoryRepository.findById(productId)
    	        .orElseThrow(() -> new RuntimeException("Id not found"));


        if (inventory == null) {
            throw new RuntimeException("Inventory not found");
        }

        inventory.setStockQuantity(dto.getAvailableQuantity());
        inventory.setThreshold(dto.getReorderLevel());
        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory updated = inventoryRepository.save(inventory);

        return mapToDTO(updated);
    }

    @GetMapping("/low-stock")
    public List<InventoryDTO> getLowStock() {
        return inventoryRepository.findAll()
                .stream()
                .filter(inv -> inv.getStockQuantity() <= inv.getThreshold())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private InventoryDTO mapToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();

        dto.setInventoryId(inventory.getInventoryId());
        dto.setAvailableQuantity(inventory.getStockQuantity());
        dto.setReorderLevel(inventory.getThreshold());
        dto.setUpdatedAt(inventory.getUpdatedAt());

        return dto;
    }
}