package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.ProductRequestDTO;
import com.group.shoppingapp.dto.ProductResponseDTO;
import com.group.shoppingapp.entity.Product;
import com.group.shoppingapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private ProductRequestDTO productRequestDTO;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Laptop");
        testProduct.setPrice(999.99);
        testProduct.setDescription("High-performance laptop");
        testProduct.setSku("SKU001");

        productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName("Laptop");
        productRequestDTO.setPrice(999.99);
        productRequestDTO.setDescription("High-performance laptop");
        productRequestDTO.setSku("SKU001");
    }

    @Test
    void testCreateProduct_Success() {
        when(productRepository.existsBySku("SKU001")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductResponseDTO result = productService.createProduct(productRequestDTO);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(999.99, result.getPrice());
        verify(productRepository, times(1)).existsBySku("SKU001");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProduct_DuplicateSku() {
        when(productRepository.existsBySku("SKU001")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            productService.createProduct(productRequestDTO);
        });

        verify(productRepository, times(1)).existsBySku("SKU001");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testCreateProduct_NullRequest() {
        assertThrows(NullPointerException.class, () -> {
            productService.createProduct(null);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testGetAllProducts_Success() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        product2.setPrice(29.99);
        product2.setSku("SKU002");

        List<Product> products = Arrays.asList(testProduct, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Mouse", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_Empty() {
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        List<ProductResponseDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        ProductResponseDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(999.99, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productService.getProductById(999L);
        });

        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testGetProductBySku_Found() {
        when(productRepository.findBySku("SKU001")).thenReturn(Optional.of(testProduct));

        ProductResponseDTO result = productService.getProductBySku("SKU001");

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals("SKU001", result.getSku());
        verify(productRepository, times(1)).findBySku("SKU001");
    }

    @Test
    void testGetProductBySku_NotFound() {
        when(productRepository.findBySku(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productService.getProductBySku("INVALID_SKU");
        });

        verify(productRepository, times(1)).findBySku("INVALID_SKU");
    }

//    @Test
//    void testUpdateProduct_Success() {
//        ProductRequestDTO updateDTO = new ProductRequestDTO();
//        updateDTO.setName("Gaming Laptop");
//        updateDTO.setPrice(1299.99);
//        updateDTO.setDescription("High-end gaming laptop");
//        updateDTO.setSku("SKU001");
//
//        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
////        when(productRepository.existsBySku("SKU001")).thenReturn(true);
//        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
//
//        ProductResponseDTO result = productService.updateProduct(1L, updateDTO);
//
//        assertNotNull(result);
//        verify(productRepository, times(1)).findById(1L);
//        verify(productRepository, times(1)).save(any(Product.class));
//    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(999L, productRequestDTO);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NewSkuAlreadyExists() {
        Product existingProduct = new Product();
        existingProduct.setId(2L);
        existingProduct.setSku("SKU002");

        ProductRequestDTO updateDTO = new ProductRequestDTO();
        updateDTO.setName("Updated Product");
        updateDTO.setSku("SKU002"); // This SKU already exists

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.existsBySku("SKU002")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1L, updateDTO);
        });

        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(999L);
        });

        verify(productRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCreateProduct_MultipleProducts() {
        when(productRepository.existsBySku(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        for (int i = 0; i < 5; i++) {
            ProductRequestDTO dto = new ProductRequestDTO();
            dto.setName("Product " + i);
            dto.setPrice(100.0 * i);
            dto.setSku("SKU" + i);

            ProductResponseDTO result = productService.createProduct(dto);
            assertNotNull(result);
        }

        verify(productRepository, times(5)).existsBySku(anyString());
        verify(productRepository, times(5)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_ChangeSku() {
        ProductRequestDTO updateDTO = new ProductRequestDTO();
        updateDTO.setName("Laptop");
        updateDTO.setPrice(999.99);
        updateDTO.setSku("SKU_NEW");

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.existsBySku("SKU_NEW")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductResponseDTO result = productService.updateProduct(1L, updateDTO);

        assertNotNull(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).existsBySku("SKU_NEW");
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
