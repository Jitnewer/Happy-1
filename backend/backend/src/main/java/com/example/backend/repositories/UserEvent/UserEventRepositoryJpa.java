package com.example.backend.repositories.UserEvent;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import com.example.backend.models.UserEvent;
import com.example.backend.repositories.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
@Primary
public class UserEventRepositoryJpa implements UserEventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEvent> getUserEvents() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<UserEvent> query = entityManager.createQuery(jpql, UserEvent.class);
        return query.getResultList();
    }

    @Override
    public UserEvent getUserEvent(long id) {
        return entityManager.find(UserEvent.class, id);

    }


    @Override
    @Transactional
    public void addUserEvent(UserEvent userEvent) {
        entityManager.persist(userEvent);
    }

    @Override
    @Transactional
    public void updateUserEvent(UserEvent userEvent) {
        entityManager.merge(userEvent);
    }

    @Override
    @Transactional
    public void deleteUserEvent(long id) {
        UserEvent userEvent = entityManager.find(UserEvent.class, id);
        if (userEvent != null) {
            entityManager.remove(userEvent);
        }
    }

    @Override
    public UserEvent getUserEventByUserAndEvent(User user, Event event) {
        String jpql = "SELECT ue FROM UserEvent ue WHERE ue.user = :user AND ue.event = :event";
        TypedQuery<UserEvent> query = entityManager.createQuery(jpql, UserEvent.class);
        query.setParameter("user", user);
        query.setParameter("event", event);

        List<UserEvent> userEvents = query.getResultList();
        return userEvents.isEmpty() ? null : userEvents.get(0);
    }
    @Override
    public int countUserEventsByEventId(long eventId) {
        String jpql = "SELECT COUNT(ue) FROM UserEvent ue WHERE ue.event.id = :eventId";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("eventId", eventId);

        return query.getSingleResult().intValue();
    }
}
