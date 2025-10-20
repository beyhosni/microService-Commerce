package com.example.payment.web;
import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.Map;

@RestController @RequestMapping("/payments")
public class PaymentController {
  private final KafkaTemplate<String,Object> kafka;
  public PaymentController(KafkaTemplate<String,Object> kafka){this.kafka=kafka;}
  @PostMapping("/capture")
  public Map<String,Object> capture(@RequestBody Map<String,Object> req){
    String orderId = String.valueOf(req.getOrDefault("orderId","unknown"));
    kafka.send("payment.captured", orderId, Map.of("orderId", orderId, "status","CAPTURED"));
    return Map.of("orderId", orderId, "status","CAPTURED");
  }
}
