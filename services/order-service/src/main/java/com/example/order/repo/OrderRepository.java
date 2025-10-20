package com.example.order.repo;
import com.example.order.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
  Optional<CustomerOrder> findByOrderId(String orderId);
}
