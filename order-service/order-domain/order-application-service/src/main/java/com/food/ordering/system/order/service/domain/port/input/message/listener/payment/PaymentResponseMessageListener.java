package com.food.ordering.system.order.service.domain.port.input.message.listener.payment;

import com.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import jakarta.validation.Valid;

public interface PaymentResponseMessageListener {

    void paymentCompleted(@Valid PaymentResponse paymentResponse);

    void paymentCancelled(@Valid PaymentResponse paymentResponse);
}
