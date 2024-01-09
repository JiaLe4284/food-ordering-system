package com.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderAddress {
    @NotBlank
    @Max(value = 50)
    private final String street;

    @NotBlank
    @Max(value = 10)
    private final String postalCode;

    @NotBlank
    @Max(value = 50)
    private final String city;
}
