package com.example.order.domain;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
public class CustomerOrder {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
  String orderId;
  String userId;
  Instant createdAt = Instant.now();
  String status = "PENDING";
  @ElementCollection
  List<OrderItem> items = new ArrayList<>();
  public CustomerOrder(){}
  public CustomerOrder(String orderId,String userId){this.orderId=orderId;this.userId=userId;}
  // getters/setters
  public Long getId(){return id;} public String getOrderId(){return orderId;} public void setOrderId(String v){this.orderId=v;}
  public String getUserId(){return userId;} public void setUserId(String v){this.userId=v;}
  public Instant getCreatedAt(){return createdAt;}
  public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
  public List<OrderItem> getItems(){return items;} public void setItems(List<OrderItem> it){this.items=it;}
  @Embeddable public static class OrderItem {
    String sku; Integer qty;
    public String getSku(){return sku;} public void setSku(String s){this.sku=s;}
    public Integer getQty(){return qty;} public void setQty(Integer q){this.qty=q;}
  }
}
