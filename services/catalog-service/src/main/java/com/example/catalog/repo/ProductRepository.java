package com.example.catalog.repo;
import com.example.catalog.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ProductRepository extends JpaRepository<Product,Long> {
  Optional<Product> findBySku(String sku);
}
