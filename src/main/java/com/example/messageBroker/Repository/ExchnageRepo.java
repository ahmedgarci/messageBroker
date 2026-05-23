package com.example.messageBroker.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.messageBroker.domain.Exchange;

@Repository
public interface ExchnageRepo extends JpaRepository<Exchange,UUID>{

    Optional<Exchange> findByName(String name);


}
