package com.example.catalog.web;
import com.example.catalog.domain.Product;
import com.example.catalog.repo.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/products")
public class ProductController {
  private final ProductRepository repo;
  public ProductController(ProductRepository repo){this.repo=repo;}
  @GetMapping public List<Product> all(){ return repo.findAll(); }
  @GetMapping("/{sku}") public Product bySku(@PathVariable String sku){ return repo.findBySku(sku).orElseThrow(); }
  @PostMapping public Product create(@RequestBody Product p){ return repo.save(p); }
  @PatchMapping("/{sku}/stock/{delta}") public Product updateStock(@PathVariable String sku,@PathVariable Integer delta){
    var p = repo.findBySku(sku).orElseThrow();
    p.setStock(p.getStock()+delta);
    return repo.save(p);
  }
}
