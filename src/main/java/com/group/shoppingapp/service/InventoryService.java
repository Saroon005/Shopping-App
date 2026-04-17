package com.group.shoppingapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.shoppingapp.dto.InventoryDTO;
import com.group.shoppingapp.entity.Inventory;
import com.group.shoppingapp.entity.Product;
import com.group.shoppingapp.repository.InventoryRepository;
import com.group.shoppingapp.repository.ProductRepository;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public InventoryDTO createInventory(InventoryDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Inventory inventory = new Inventory();
        inventory.setStockQuantity(dto.getAvailableQuantity());
        inventory.setThreshold(dto.getReorderLevel());
        inventory.setCreatedAt(LocalDateTime.now());
        inventory.setUpdatedAt(LocalDateTime.now());
        inventory.setProduct(product);

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
        List<Inventory> list = inventoryRepository.findAll();
        List<InventoryDTO> result = new java.util.ArrayList<>();

        for (Inventory inv : list) {
            if (inv.getStockQuantity() <= inv.getThreshold()) {
                result.add(mapToDTO(inv));
            }
        }

        return result;
    }
    
    private InventoryDTO mapToDTO(Inventory inventory) {

        InventoryDTO dto = new InventoryDTO();

        dto.setInventoryId(inventory.getInventoryId());
        dto.setAvailableQuantity(inventory.getStockQuantity());
        dto.setReorderLevel(inventory.getThreshold());
        dto.setUpdatedAt(inventory.getUpdatedAt());

        if (inventory.getProduct() != null) {
            dto.setProductId(inventory.getProduct().getId());
        }

        return dto;
    }
}