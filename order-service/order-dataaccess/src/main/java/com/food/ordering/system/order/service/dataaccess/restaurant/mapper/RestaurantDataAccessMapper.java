package com.food.ordering.system.order.service.dataaccess.restaurant.mapper;

import com.food.ordering.system.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> toRestaurantProductsFrom(Restaurant restaurant) {
        return restaurant.getProductMap()
                .entrySet()
                .stream()
                .map(res -> res.getKey().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant toRestaurantFrom(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("Restaurant could not be found!"));

        Map<ProductId, Product> restaurantProducts = restaurantEntities.stream().collect(Collectors.toMap(
                resEn -> new ProductId(resEn.getProductId()),
                resEn -> new Product(new ProductId(resEn.getProductId()), resEn.getProductName(),
                        new Money(resEn.getProductPrice()))
        ));

        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .isActive(restaurantEntity.getRestaurantActive())
                .build();
    }
}
