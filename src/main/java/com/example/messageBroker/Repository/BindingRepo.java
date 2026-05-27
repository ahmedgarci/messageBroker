package com.example.messageBroker.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.messageBroker.domain.Binding;

@Repository
public interface BindingRepo extends JpaRepository<Binding,UUID>{

    @Query(value="SELECT * FROM BINDING AS b WHERE b.exchange_id = :exchangeId", nativeQuery = true)
    List<Binding> findByExchangeAndRoutingKey(@Param("exchangeId") UUID exchangeId);

    boolean existsByExchangeNameAndQueueNameAndRoutingKey(String exchangeName, String queueName, String routingKey);


}
