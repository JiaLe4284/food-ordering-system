package com.food.ordering.system.order.service.domain.port.input.message.listener.restaurantapproval;

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import jakarta.validation.Valid;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(@Valid RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(@Valid RestaurantApprovalResponse restaurantApprovalResponse);
}
