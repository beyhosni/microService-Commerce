package com.example.inventory.messaging;
import com.example.inventory.domain.Reservation;
import com.example.inventory.repo.ReservationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
  private final ReservationRepository repo;
  public OrderCreatedListener(ReservationRepository repo){this.repo=repo;}
  record OrderCreated(String orderId, String userId, java.util.List<Item> items){
    record Item(String sku,Integer qty){}
  }
  @KafkaListener(topics="order.created", groupId="inventory")
  public void on(OrderCreated evt){
    if(evt.items()!=null){
      evt.items().forEach(i->{
        var r=new Reservation();
        r.setOrderId(evt.orderId()); r.setSku(i.sku()); r.setQty(i.qty());
        repo.save(r);
      });
    }
  }
}
