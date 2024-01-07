package com.food.ordering.system.order.service.domain.valueobject;

import com.food.ordering.system.domain.valueobject.BaseId;

import java.util.UUID;

public class StreetAddress extends BaseId<UUID> {
    private final String street;
    private final String postalCode;
    private final String city;

    public StreetAddress(UUID id, String street, String postalCode, String city) {
        super(id);
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
