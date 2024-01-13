package com.food.ordering.system.order.service;

import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.create.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.service.domain.port.input.service.OrderApplicationService;
import com.food.ordering.system.order.service.domain.port.output.repository.CustomerRepository;
import com.food.ordering.system.order.service.domain.port.output.repository.OrderRepository;
import com.food.ordering.system.order.service.domain.port.output.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private final UUID CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID RESTAURANT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45");
    private final UUID PRODUCT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private final UUID ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @Test
    void testCreateOrder() {
        //given
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(4)
                        .price(new BigDecimal("50.00"))
                        .subtotal(new BigDecimal("200.00"))
                        .build()))
                .build();

        // when
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(new Customer(new CustomerId(CUSTOMER_ID))));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.toRestaurantFrom(createOrderCommand)))
                .thenReturn(Optional.of(Restaurant.builder()
                        .restaurantId(new RestaurantId(RESTAURANT_ID))
                        .products(Map.of(
                                new ProductId(PRODUCT_ID), new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00")))
                        ))
                        .isActive(true)
                        .build()));
        Order order = orderDataMapper.toOrderFrom(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);

        // Assert Result
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    void testCreateOrderWithWrongTotalPrice() {
        // given
        CreateOrderCommand createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(4)
                                .price(new BigDecimal("50.00"))
                                .subtotal(new BigDecimal("200.00"))
                                .build()))
                .build();

        // when
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(new Customer(new CustomerId(CUSTOMER_ID))));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.toRestaurantFrom(createOrderCommandWrongPrice)))
                .thenReturn(Optional.of(Restaurant.builder()
                        .restaurantId(new RestaurantId(RESTAURANT_ID))
                        .products(Map.of(
                                new ProductId(PRODUCT_ID), new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00")))
                        ))
                        .isActive(true)
                        .build()));

        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));
        assertEquals("Total price: 250.00 is not equal to Order items total: 200.00!", orderDomainException.getMessage());
    }

    @Test
    void testCreateOrderWithWrongProductPrice() {
        // given
        CreateOrderCommand createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("120.00"))
                .items(List.of(OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(4)
                        .price(new BigDecimal("30.00"))
                        .subtotal(new BigDecimal("120.00"))
                        .build()))
                .build();

        // when
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(new Customer(new CustomerId(CUSTOMER_ID))));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.toRestaurantFrom(createOrderCommandWrongProductPrice)))
                .thenReturn(Optional.of(Restaurant.builder()
                        .restaurantId(new RestaurantId(RESTAURANT_ID))
                        .products(Map.of(
                                new ProductId(PRODUCT_ID), new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00")))
                        ))
                        .isActive(true)
                        .build()));

        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
        assertEquals("Order item price: 30.00 is not valid for product " + PRODUCT_ID, orderDomainException.getMessage());
    }

    @Test
    void testCreateOrderWithPassiveRestaurant() {
        //given
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(4)
                        .price(new BigDecimal("50.00"))
                        .subtotal(new BigDecimal("200.00"))
                        .build()))
                .build();

        // when
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(new Customer(new CustomerId(CUSTOMER_ID))));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.toRestaurantFrom(createOrderCommand)))
                .thenReturn(Optional.of(Restaurant.builder()
                        .restaurantId(new RestaurantId(RESTAURANT_ID))
                        .products(Map.of(
                                new ProductId(PRODUCT_ID), new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00")))
                        ))
                        .isActive(false)
                        .build()));

        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommand));

        // then
        assertEquals("Restaurant with id " + RESTAURANT_ID + " is currently not active!", orderDomainException.getMessage());
    }
}
