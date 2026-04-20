package com.group.shoppingapp;

import com.group.shoppingapp.dto.*;
import com.group.shoppingapp.entity.*;
import com.group.shoppingapp.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Tests for Shopping App
 * Tests the interaction between multiple modules and services
 */
@SpringBootTest
class ShoppingAppIntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private NotificationService notificationService;

    @Test
    void testCompleteOrderWorkflow() {
        // Step 1: Create a User
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@example.com");
        userDTO.setNumber(1234567890L);
        userDTO.setRole("CUSTOMER");

        userService.createUser(userDTO);
        List<UserDTO> users = userService.getAllUsers();
        assertTrue(users.size() > 0);

        Long userId = users.get(0).getId();

        // Step 2: Create Products
        ProductRequestDTO productDTO1 = new ProductRequestDTO();
        productDTO1.setName("Gaming Laptop");
        productDTO1.setPrice(1299.99);
        productDTO1.setDescription("High-performance gaming laptop");
        productDTO1.setSku("GAMING_LAPTOP_001");

        ProductResponseDTO createdProduct1 = productService.createProduct(productDTO1);
        assertNotNull(createdProduct1);

        ProductRequestDTO productDTO2 = new ProductRequestDTO();
        productDTO2.setName("Wireless Mouse");
        productDTO2.setPrice(49.99);
        productDTO2.setDescription("Ergonomic wireless mouse");
        productDTO2.setSku("MOUSE_001");

        ProductResponseDTO createdProduct2 = productService.createProduct(productDTO2);
        assertNotNull(createdProduct2);

        // Step 3: Create Inventory for Products
        InventoryDTO inventoryDTO1 = new InventoryDTO();
        inventoryDTO1.setProductId(createdProduct1.getId());
        inventoryDTO1.setAvailableQuantity(10);
        inventoryDTO1.setReorderLevel(5);

        InventoryDTO createdInventory1 = inventoryService.createInventory(inventoryDTO1);
        assertNotNull(createdInventory1);

        InventoryDTO inventoryDTO2 = new InventoryDTO();
        inventoryDTO2.setProductId(createdProduct2.getId());
        inventoryDTO2.setAvailableQuantity(100);
        inventoryDTO2.setReorderLevel(20);

        InventoryDTO createdInventory2 = inventoryService.createInventory(inventoryDTO2);
        assertNotNull(createdInventory2);

        // Step 4: Create an Order
        OrderItemDTO orderItem1 = new OrderItemDTO();
        orderItem1.setProduct_id(createdProduct1.getId());
        orderItem1.setQuantity(1);

        OrderItemDTO orderItem2 = new OrderItemDTO();
        orderItem2.setProduct_id(createdProduct2.getId());
        orderItem2.setQuantity(2);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUser_id(userId);
        orderDTO.setOrderItemsList(List.of(orderItem1, orderItem2));

        Order createdOrder = orderService.createOrder(orderDTO);
        assertNotNull(createdOrder);
        assertEquals(OrderStatus.CREATED, createdOrder.getOrderStatus());

        // Step 5: Verify Order is retrievable
        Order fetchedOrder = orderService.fetchOrder(createdOrder.getId());
        assertNotNull(fetchedOrder);
        assertEquals(createdOrder.getId(), fetchedOrder.getId());

        // Step 6: Check orders by user
        List<Order> userOrders = orderService.fetchOrdersByUser(userId);
        assertTrue(userOrders.size() > 0);

        // Step 7: Create Notification
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUser_id(userId);
        notificationRequest.setNotificationType("CHECKOUT_CONFIRMATION");
        notificationRequest.setMessage("Your order #" + createdOrder.getId() + " has been placed successfully");

        NotificationResponse notification = notificationService.createNotification(notificationRequest);
        assertNotNull(notification);
        assertEquals("PENDING", notification.getStatus());

        // Step 8: Retrieve all notifications
        List<NotificationResponse> allNotifications = notificationService.getAllNotifications();
        assertTrue(allNotifications.size() > 0);
    }

    @Test
    void testLowStockNotificationWorkflow() {
        // Create product with low inventory
        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setName("Low Stock Item");
        productDTO.setPrice(99.99);
        productDTO.setSku("LOW_STOCK_ITEM_001");

        ProductResponseDTO product = productService.createProduct(productDTO);

        // Create inventory with stock below threshold
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setProductId(product.getId());
        inventoryDTO.setAvailableQuantity(5);
        inventoryDTO.setReorderLevel(20);

        inventoryService.createInventory(inventoryDTO);

        // Check low stock items
        List<InventoryDTO> lowStockItems = inventoryService.getLowStock();
        assertTrue(lowStockItems.stream().anyMatch(item -> item.getProductId().equals(product.getId())));
    }

    @Test
    void testProductUpdateAndInventorySync() {
        // Create a product
        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setName("Original Product");
        productDTO.setPrice(100.0);
        productDTO.setSku("UPDATE_TEST_001");

        ProductResponseDTO createdProduct = productService.createProduct(productDTO);

        // Update the product
        ProductRequestDTO updateDTO = new ProductRequestDTO();
        updateDTO.setName("Updated Product");
        updateDTO.setPrice(150.0);
        updateDTO.setSku("UPDATE_TEST_001");

        ProductResponseDTO updatedProduct = productService.updateProduct(createdProduct.getId(), updateDTO);
        assertNotNull(updatedProduct);

        // Verify product is updated
        ProductResponseDTO fetchedProduct = productService.getProductById(createdProduct.getId());
        assertNotNull(fetchedProduct);
        assertEquals("Updated Product", fetchedProduct.getName());
        assertEquals(150.0, fetchedProduct.getPrice());
    }

    @Test
    void testUserManagement() {
        // Create multiple users
        for (int i = 0; i < 3; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName("User" + i);
            userDTO.setLastName("Test" + i);
            userDTO.setEmail("user" + i + "@example.com");
            userDTO.setNumber(1000000000L + i);
            userDTO.setRole("CUSTOMER");

            userService.createUser(userDTO);
        }

        // Fetch all users
        List<UserDTO> allUsers = userService.getAllUsers();
        assertTrue(allUsers.size() >= 3);

        // Update a user
        Long userId = allUsers.get(0).getId();
        UserDTO updateDTO = new UserDTO();
        updateDTO.setFirstName("UpdatedUser");
        updateDTO.setLastName("UpdatedTest");
        updateDTO.setEmail("updated@example.com");
        updateDTO.setNumber(9999999999L);
        updateDTO.setRole("ADMIN");

        userService.updateUser(userId, updateDTO);

        // Verify update
        UserDTO fetchedUser = userService.getUserById(userId);
        assertNotNull(fetchedUser);
        assertEquals("UpdatedUser", fetchedUser.getFirstName());
    }

    @Test
    void testProductCatalog() {
        // Create multiple products with unique SKUs
        for (int i = 0; i < 5; i++) {
            ProductRequestDTO productDTO = new ProductRequestDTO();
            productDTO.setName("Product " + i);
            productDTO.setPrice(50.0 + (i * 10));
            productDTO.setSku("PRODUCT_" + i + "_SKU_" + System.currentTimeMillis());
            productDTO.setDescription("Description for product " + i);

            ProductResponseDTO createdProduct = productService.createProduct(productDTO);
            assertNotNull(createdProduct);
        }

        // Verify all products are in catalog
        List<ProductResponseDTO> allProducts = productService.getAllProducts();
        assertTrue(allProducts.size() >= 5);
    }

    @Test
    void testNotificationSystem() {
        // Create a user for notifications
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("NotificationTest");
        userDTO.setLastName("User");
        userDTO.setEmail("notif@example.com");
        userDTO.setNumber(5555555555L);
        userDTO.setRole("CUSTOMER");

        userService.createUser(userDTO);
        List<UserDTO> users = userService.getAllUsers();
        Long userId = users.stream()
                .filter(u -> "NotificationTest".equals(u.getFirstName()))
                .findFirst()
                .map(UserDTO::getId)
                .orElse(null);

        if (userId != null) {
            // Create multiple notifications
            for (int i = 0; i < 3; i++) {
                NotificationRequest request = new NotificationRequest();
                request.setUser_id(userId);
                request.setNotificationType(i % 2 == 0 ? "CHECKOUT_CONFIRMATION" : "LOW_STOCK");
                request.setMessage("Notification message " + i);

                NotificationResponse response = notificationService.createNotification(request);
                assertNotNull(response);
                assertEquals("PENDING", response.getStatus());
            }

            // Verify notifications exist
            List<NotificationResponse> allNotifications = notificationService.getAllNotifications();
            assertTrue(allNotifications.size() >= 3);
        }
    }

    @Test
    void testInventoryManagement() {
        // Create a product
        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setName("Inventory Test Product");
        productDTO.setPrice(99.99);
        productDTO.setSku("INV_TEST_" + System.currentTimeMillis());

        ProductResponseDTO product = productService.createProduct(productDTO);

        // Create inventory
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setProductId(product.getId());
        inventoryDTO.setAvailableQuantity(100);
        inventoryDTO.setReorderLevel(25);

        InventoryDTO createdInventory = inventoryService.createInventory(inventoryDTO);
        assertNotNull(createdInventory);

        // Update inventory
        InventoryDTO updateDTO = new InventoryDTO();
        updateDTO.setAvailableQuantity(75);
        updateDTO.setReorderLevel(30);

        InventoryDTO updatedInventory = inventoryService.updateInventory(createdInventory.getInventoryId(), updateDTO);
        assertNotNull(updatedInventory);

        // Verify inventory update
        InventoryDTO fetchedInventory = inventoryService.getById(createdInventory.getInventoryId());
        assertNotNull(fetchedInventory);
        assertEquals(75, fetchedInventory.getAvailableQuantity());
        assertEquals(30, fetchedInventory.getReorderLevel());
    }
}
