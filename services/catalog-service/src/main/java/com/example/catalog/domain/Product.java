package com.example.catalog.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
  @Column(unique=true, nullable=false) String sku;
  String name;
  BigDecimal price;
  Integer stock;
  public Product() {}
  public Product(String sku,String name,BigDecimal price,Integer stock){this.sku=sku;this.name=name;this.price=price;this.stock=stock;}
  // getters/setters omitted for brevity
  public Long getId(){return id;}
  public String getSku(){return sku;} public void setSku(String v){this.sku=v;}
  public String getName(){return name;} public void setName(String v){this.name=v;}
  public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){this.price=v;}
  public Integer getStock(){return stock;} public void setStock(Integer v){this.stock=v;}
}
