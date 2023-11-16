package com.example.backend.repositories.UserEvent;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.models.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserEventSpringDataJpaRepository extends JpaRepository<UserEvent, Long> {
    @Query("SELECT COUNT(ue) FROM UserEvent ue WHERE ue.event = :event")
    int countByEvent(@Param("event") Event event);

    boolean existsByUserAndEvent(User user, Event event);

    Optional<UserEvent> findByUserAndEvent(User user, Event event);


}
