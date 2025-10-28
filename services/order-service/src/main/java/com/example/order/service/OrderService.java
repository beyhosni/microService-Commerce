package com.example.order.service;

import com.example.order.domain.CustomerOrder;
import com.example.order.repo.OrderRepository;
import com.example.order.messaging.OrderCreated;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.Map;

/**
 * Service pour gérer les opérations sur les commandes.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderRepository orderRepository, 
                        KafkaTemplate<String, Object> kafkaTemplate,
                        OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.orderEventPublisher = orderEventPublisher;
    }

    /**
     * Crée une nouvelle commande.
     * @param order La commande à créer
     * @return La commande créée avec un ID généré
     */
    @Transactional
    public CustomerOrder createOrder(CustomerOrder order) {
        // Générer un ID unique pour la commande
        order.setOrderId(UUID.randomUUID().toString());

        // Sauvegarder la commande
        CustomerOrder savedOrder = orderRepository.save(order);

        // Publier un événement de commande créée
        orderEventPublisher.publishOrderCreated(savedOrder);

        return savedOrder;
    }

    /**
     * Récupère une commande par son ID.
     * @param orderId L'ID de la commande
     * @return La commande correspondante
     * @throws OrderNotFoundException si la commande n'existe pas
     */
    public CustomerOrder getOrderById(String orderId) {
        return orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID: " + orderId));
    }

    /**
     * Traite le paiement d'une commande.
     * @param orderId L'ID de la commande à payer
     * @param paymentClient Le client de paiement
     * @return Le résultat du traitement du paiement
     * @throws OrderNotFoundException si la commande n'existe pas
     */
    public CompletableFuture<Map<String, Object>> processPayment(String orderId, 
                                                               com.example.order.client.PaymentClient paymentClient) {
        // Vérifier si la commande existe
        getOrderById(orderId);

        // Traiter le paiement
        return paymentClient.capture(orderId);
    }
}
