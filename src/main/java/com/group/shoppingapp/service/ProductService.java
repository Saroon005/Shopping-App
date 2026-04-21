package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.ProductRequestDTO;
import com.group.shoppingapp.dto.ProductResponseDTO;
import com.group.shoppingapp.entity.Product;
import com.group.shoppingapp.exception.InvalidProductException;
import com.group.shoppingapp.exception.ProductNotFoundException;
import com.group.shoppingapp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO request) {

        if (productRepository.existsBySku(request.getSku())) {
            throw new InvalidProductException("Product with this SKU already exists");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setSku(request.getSku());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (!product.getSku().equals(request.getSku()) &&
                productRepository.existsBySku(request.getSku())) {
            throw new InvalidProductException("SKU already exists");
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setSku(request.getSku());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    public String deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);
        return "deleted successfully";
    }

    private ProductResponseDTO mapToResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getSku(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
    
    public ProductResponseDTO getProductBySku(String sku) {

        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with SKU"));

        return mapToResponse(product);
    }
}