package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.exception.DomainException;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;

import java.util.List;
import java.util.Map;

public class Restaurant extends AggregateRoot<RestaurantId> {
    private Map<ProductId, Product> productMap;
    private boolean isActive;

    private Restaurant(Builder builder) {
        super.setId(builder.restaurantId);
        productMap = builder.productMap;
        isActive = builder.isActive;
    }

    public void validate() {
        if (!isActive()) {
            throw new OrderDomainException("Restaurant with id " + getId().getValue() +
                    " is currently not active!");
        }
    }

    public Map<ProductId, Product> getProductMap() {
        return productMap;
    }

    public boolean isActive() {
        return isActive;
    }

    public static final class Builder {
        private RestaurantId restaurantId;
        private Map<ProductId, Product> productMap;
        private boolean isActive;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder products(Map<ProductId, Product> val) {
            productMap = val;
            return this;
        }

        public Builder isActive(boolean val) {
            isActive = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
