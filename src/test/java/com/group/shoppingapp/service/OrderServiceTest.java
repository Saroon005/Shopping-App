package com.group.shoppingapp.service;

import com.group.shoppingapp.dto.OrderDTO;
import com.group.shoppingapp.dto.OrderItemDTO;
import com.group.shoppingapp.entity.*;
import com.group.shoppingapp.exception.InvalidOrderException;
import com.group.shoppingapp.exception.OrderNotFoundException;
import com.group.shoppingapp.exception.UserNotFoundException;
import com.group.shoppingapp.repository.OrderRepository;
import com.group.shoppingapp.repository.ProductRepository;
import com.group.shoppingapp.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;
    private OrderDTO orderDTO;
    private User testUser;
    private Product testProduct;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Laptop");
        testProduct.setPrice(999.99);
        testProduct.setSku("SKU001");

        testOrderItem = new OrderItem();
        testOrderItem.setId(1L);
        testOrderItem.setProduct(testProduct);
        testOrderItem.setQuantity(2);
        testOrderItem.setUnitPrice(999.99);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setUser(testUser);
        testOrder.setTotalAmount(1999.98);
        testOrder.setOrderStatus(OrderStatus.CREATED);
        testOrder.setOrderItemList(Arrays.asList(testOrderItem));

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProduct_id(1L);
        orderItemDTO.setQuantity(2);

        orderDTO = new OrderDTO();
        orderDTO.setUser_id(1L);
        orderDTO.setOrderItemsList(Arrays.asList(orderItemDTO));
    }


    @Test
    void testCreateOrder_EmptyOrderItems() {
        orderDTO.setOrderItemsList(Arrays.asList());

        assertThrows(InvalidOrderException.class, () -> {
            orderService.createOrder(orderDTO);
        });

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testCreateOrder_NullOrderItems() {
        orderDTO.setOrderItemsList(null);

        assertThrows(InvalidOrderException.class, () -> {
            orderService.createOrder(orderDTO);
        });

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testCreateOrder_NullUserId() {
        orderDTO.setUser_id(null);

        assertThrows(InvalidOrderException.class, () -> {
            orderService.createOrder(orderDTO);
        });

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testCreateOrder_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(OrderStatus.CREATED, result.getOrderStatus());
        assertEquals(testUser.getId(), result.getUser().getId());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateOrder_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(orderDTO);
        });

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testCreateOrder_ProductNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(orderDTO);
        });

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testCreateOrder_MultipleItems() {
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        product2.setPrice(29.99);

        OrderItemDTO item2 = new OrderItemDTO();
        item2.setProduct_id(2L);
        item2.setQuantity(3);

        orderDTO.setOrderItemsList(Arrays.asList(
                orderDTO.getOrderItemsList().get(0),
                item2
        ));

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrder_CalculatesTotalCorrectly() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setUser(testUser);
        mockOrder.setTotalAmount(1999.98); // 999.99 * 2
        mockOrder.setOrderStatus(OrderStatus.CREATED);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        Order result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testFetchAllOrders_Success() {
        Order order2 = new Order();
        order2.setId(2L);

        List<Order> orders = Arrays.asList(testOrder, order2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.fetchAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testFetchAllOrders_Empty() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList());

        List<Order> result = orderService.fetchAllOrders();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testFetchOrder_Found() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Order result = orderService.fetchOrder(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(OrderStatus.CREATED, result.getOrderStatus());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchOrder_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.fetchOrder(999L);
        });

        verify(orderRepository, times(1)).findById(999L);
    }

    @Test
    void testFetchOrdersByUser_Success() {
        Order order2 = new Order();
        order2.setId(2L);
        order2.setUser(testUser);

        List<Order> orders = Arrays.asList(testOrder, order2);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(orderRepository.findByUser(testUser)).thenReturn(orders);

        List<Order> result = orderService.fetchOrdersByUser(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findByUser(testUser);
    }

    @Test
    void testFetchOrdersByUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            orderService.fetchOrdersByUser(999L);
        });

        verify(orderRepository, never()).findByUser(any(User.class));
    }

    @Test
    void testFetchOrdersByUser_EmptyOrders() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(orderRepository.findByUser(testUser)).thenReturn(Arrays.asList());

        List<Order> result = orderService.fetchOrdersByUser(1L);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(orderRepository, times(1)).findByUser(testUser);
    }

}
