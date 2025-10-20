package com.example.order.client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class PaymentClient {
  private final WebClient client;
  public PaymentClient(WebClient.Builder builder, @Value("${payment.base-url:http://payment-service}") String baseUrl){
    this.client = builder.baseUrl(baseUrl).build();
  }

  @CircuitBreaker(name="paymentCb", fallbackMethod="fallback")
  @TimeLimiter(name="paymentCb")
  public CompletableFuture<Map> capture(String orderId){
    return client.post().uri("/payments/capture")
        .bodyValue(Map.of("orderId", orderId))
        .retrieve()
        .bodyToMono(Map.class)
        .timeout(java.time.Duration.ofSeconds(3))
        .toFuture();
  }

  private CompletableFuture<Map> fallback(String orderId, Throwable t){
    return CompletableFuture.completedFuture(Map.of("orderId", orderId, "status", "DEFERRED", "reason", "payment_down"));
  }
}
