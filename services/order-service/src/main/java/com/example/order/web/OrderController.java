package com.example.order.web;
import com.example.order.domain.CustomerOrder;
import com.example.order.repo.OrderRepository;
import com.example.order.messaging.OrderCreated;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController @RequestMapping("/orders")
public class OrderController {
  private final OrderRepository repo;
  private final KafkaTemplate<String, Object> kafka;
  public OrderController(OrderRepository repo, KafkaTemplate<String,Object> kafka){this.repo=repo;this.kafka=kafka;}
  @PostMapping
  public CustomerOrder create(@RequestBody CustomerOrder req){
    req.setOrderId(UUID.randomUUID().toString());
    var saved = repo.save(req);
    kafka.send("order.created", saved.getOrderId(), 
      new OrderCreated(saved.getOrderId(), saved.getUserId(),
        saved.getItems().stream().map(i-> new OrderCreated.Item(i.getSku(), i.getQty())).toList(),
        java.time.Instant.now()));
    return saved;
  }
  @GetMapping("/{orderId}") public CustomerOrder byId(@PathVariable String orderId){ 
    return repo.findByOrderId(orderId).orElseThrow(); 
  }

  @PostMapping("/pay/{orderId}")
  public java.util.Map<String,Object> pay(@PathVariable String orderId, com.example.order.client.PaymentClient client) throws Exception {
    return client.capture(orderId).get();
  }

}
