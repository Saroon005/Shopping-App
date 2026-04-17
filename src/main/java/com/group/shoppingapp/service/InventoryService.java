package com.group.shoppingapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.shoppingapp.dto.InventoryDTO;
import com.group.shoppingapp.entity.Inventory;
import com.group.shoppingapp.repository.InventoryRepository;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryDTO createInventory(InventoryDTO dto) {
        Inventory inventory = new Inventory();

        inventory.setStockQuantity(dto.getAvailableQuantity());
        inventory.setThreshold(dto.getReorderLevel());
        inventory.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(inventoryRepository.save(inventory));
    }

    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public InventoryDTO getById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        return mapToDTO(inventory);
    }

    public InventoryDTO updateInventory(Long id, InventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setStockQuantity(dto.getAvailableQuantity());
        inventory.setThreshold(dto.getReorderLevel());
        inventory.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(inventoryRepository.save(inventory));
    }

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