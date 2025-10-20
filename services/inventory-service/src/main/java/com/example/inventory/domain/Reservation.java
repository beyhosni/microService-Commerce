package com.example.inventory.domain;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Reservation {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
  String orderId;
  String sku;
  Integer qty;
  Instant createdAt = Instant.now();
  public Long getId(){return id;}
  public String getOrderId(){return orderId;} public void setOrderId(String v){this.orderId=v;}
  public String getSku(){return sku;} public void setSku(String v){this.sku=v;}
  public Integer getQty(){return qty;} public void setQty(Integer v){this.qty=v;}
}
