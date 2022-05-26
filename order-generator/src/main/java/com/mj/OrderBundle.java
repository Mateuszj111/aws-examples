package com.mj;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OrderBundle<T> {

    @JsonProperty("bundler_id")
    private String bundleId;

    @JsonProperty("order_items")
    private List<T> orderItems;

    @JsonProperty("order_timestamp")
    private String orderTimestamp;

    @JsonProperty("order_issuer")
    private String orderIssuer;

    public OrderBundle() {}

    public OrderBundle(List<T> orderItems, String orderIssuer) {
        this.bundleId = UUID.randomUUID().toString();
        this.orderItems = orderItems;
        this.orderTimestamp = Instant.now().toString();
        this.orderIssuer = orderIssuer;
    }

    public String bundleId() {
        return bundleId;
    }

    public List<T> getOrderItems() {
        return orderItems;
    }

    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public String getOrderIssuer() {
        return orderIssuer;
    }

    @Override
    public String toString() {
        return String.format("Order{bundleId='%s', orderIssuer='%s', orderTimestamp='%s', orderItems=%s}",
                bundleId, orderIssuer, orderTimestamp, Arrays.toString(orderItems.toArray()));
    }
}
