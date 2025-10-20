package com.example.order.messaging;
import java.time.Instant;
import java.util.List;

public record OrderCreated(String orderId, String userId, List<Item> items, Instant at) {
  public record Item(String sku, Integer qty) {}
}
