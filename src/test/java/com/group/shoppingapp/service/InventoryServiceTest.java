package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.InventoryDTO;
import com.group.shoppingapp.entity.Inventory;
import com.group.shoppingapp.entity.Product;
import com.group.shoppingapp.repository.InventoryRepository;
import com.group.shoppingapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory testInventory;
    private InventoryDTO inventoryDTO;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Laptop");
        testProduct.setPrice(999.99);
        testProduct.setSku("SKU001");

        testInventory = new Inventory();
        testInventory.setInventoryId(1L);
        testInventory.setProduct(testProduct);
        testInventory.setStockQuantity(50);
        testInventory.setThreshold(10);
        testInventory.setCreatedAt(LocalDateTime.now());
        testInventory.setUpdatedAt(LocalDateTime.now());

        inventoryDTO = new InventoryDTO();
        inventoryDTO.setProductId(1L);
        inventoryDTO.setAvailableQuantity(50);
        inventoryDTO.setReorderLevel(10);
    }

    @Test
    void testCreateInventory_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(testInventory);

        InventoryDTO result = inventoryService.createInventory(inventoryDTO);

        assertNotNull(result);
        assertEquals(1L, result.getInventoryId());
        assertEquals(50, result.getAvailableQuantity());
        assertEquals(10, result.getReorderLevel());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateInventory_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            inventoryService.createInventory(inventoryDTO);
        });

        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testCreateInventory_NullDTO() {
        assertThrows(NullPointerException.class, () -> {
            inventoryService.createInventory(null);
        });

        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testCreateInventory_MappingCorrect() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        InventoryDTO dto = new InventoryDTO();
        dto.setProductId(1L);
        dto.setAvailableQuantity(100);
        dto.setReorderLevel(20);

        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> {
            Inventory inv = invocation.getArgument(0);
            inv.setInventoryId(1L);
            return inv;
        });

        InventoryDTO result = inventoryService.createInventory(dto);

        assertNotNull(result);
        assertEquals(100, result.getAvailableQuantity());
        assertEquals(20, result.getReorderLevel());
    }

    @Test
    void testGetAllInventory_Success() {
        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(2L);
        inventory2.setStockQuantity(75);
        inventory2.setThreshold(15);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        inventory2.setProduct(product2);

        List<Inventory> inventories = Arrays.asList(testInventory, inventory2);
        when(inventoryRepository.findAll()).thenReturn(inventories);

        List<InventoryDTO> result = inventoryService.getAllInventory();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(50, result.get(0).getAvailableQuantity());
        assertEquals(75, result.get(1).getAvailableQuantity());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllInventory_Empty() {
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList());

        List<InventoryDTO> result = inventoryService.getAllInventory();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetById_Found() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(testInventory));

        InventoryDTO result = inventoryService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getInventoryId());
        assertEquals(50, result.getAvailableQuantity());
        assertEquals(10, result.getReorderLevel());
        verify(inventoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            inventoryService.getById(999L);
        });

        verify(inventoryRepository, times(1)).findById(999L);
    }

    @Test
    void testUpdateInventory_Success() {
        InventoryDTO updateDTO = new InventoryDTO();
        updateDTO.setAvailableQuantity(100);
        updateDTO.setReorderLevel(20);

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(testInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(testInventory);

        InventoryDTO result = inventoryService.updateInventory(1L, updateDTO);

        assertNotNull(result);
        verify(inventoryRepository, times(1)).findById(1L);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testUpdateInventory_NotFound() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            inventoryService.updateInventory(999L, inventoryDTO);
        });

        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void testUpdateInventory_UpdatesTimestamp() {
        InventoryDTO updateDTO = new InventoryDTO();
        updateDTO.setAvailableQuantity(75);
        updateDTO.setReorderLevel(15);

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(testInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> {
            Inventory inv = invocation.getArgument(0);
            assertNotNull(inv.getUpdatedAt());
            return inv;
        });

        InventoryDTO result = inventoryService.updateInventory(1L, updateDTO);

        assertNotNull(result);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testGetLowStock_Success() {
        Inventory lowStockInventory1 = new Inventory();
        lowStockInventory1.setInventoryId(1L);
        lowStockInventory1.setStockQuantity(5);
        lowStockInventory1.setThreshold(10);

        Inventory lowStockInventory2 = new Inventory();
        lowStockInventory2.setInventoryId(2L);
        lowStockInventory2.setStockQuantity(15);
        lowStockInventory2.setThreshold(15);

        Inventory adequateStockInventory = new Inventory();
        adequateStockInventory.setInventoryId(3L);
        adequateStockInventory.setStockQuantity(100);
        adequateStockInventory.setThreshold(20);

        List<Inventory> allInventories = Arrays.asList(
                lowStockInventory1,
                lowStockInventory2,
                adequateStockInventory
        );

        when(inventoryRepository.findAll()).thenReturn(allInventories);

        List<InventoryDTO> result = inventoryService.getLowStock();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(inv -> inv.getInventoryId() == 1L));
        assertTrue(result.stream().anyMatch(inv -> inv.getInventoryId() == 2L));
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetLowStock_NoLowStockItems() {
        Inventory adequateInventory1 = new Inventory();
        adequateInventory1.setInventoryId(1L);
        adequateInventory1.setStockQuantity(100);
        adequateInventory1.setThreshold(10);

        Inventory adequateInventory2 = new Inventory();
        adequateInventory2.setInventoryId(2L);
        adequateInventory2.setStockQuantity(50);
        adequateInventory2.setThreshold(20);

        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(adequateInventory1, adequateInventory2));

        List<InventoryDTO> result = inventoryService.getLowStock();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetLowStock_AllLowStock() {
        Inventory lowStockInventory1 = new Inventory();
        lowStockInventory1.setInventoryId(1L);
        lowStockInventory1.setStockQuantity(5);
        lowStockInventory1.setThreshold(10);

        Inventory lowStockInventory2 = new Inventory();
        lowStockInventory2.setInventoryId(2L);
        lowStockInventory2.setStockQuantity(8);
        lowStockInventory2.setThreshold(15);

        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(lowStockInventory1, lowStockInventory2));

        List<InventoryDTO> result = inventoryService.getLowStock();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetLowStock_StockEqualToThreshold() {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(1L);
        inventory.setStockQuantity(10);
        inventory.setThreshold(10);

        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory));

        List<InventoryDTO> result = inventoryService.getLowStock();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateInventory_MultipleInventories() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(testProduct));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(testInventory);

        for (int i = 0; i < 5; i++) {
            InventoryDTO dto = new InventoryDTO();
            dto.setProductId(1L);
            dto.setAvailableQuantity(50 + i * 10);
            dto.setReorderLevel(10);

            InventoryDTO result = inventoryService.createInventory(dto);
            assertNotNull(result);
        }

        verify(inventoryRepository, times(5)).save(any(Inventory.class));
    }
}
