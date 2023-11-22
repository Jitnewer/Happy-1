package com.example.backend.repositories.user;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Repository
@Primary
public class UserRepositoryJpa implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);

    }

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
    }


    @Override
    public User login(String email, String password) {
        String jpql = "SELECT u FROM User u WHERE u.mail = :email AND u.password = :password";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        List<User> resultList = query.getResultList();

        // Check if the query returned a user
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }

        return null; // Return null if no user with the given credentials is found
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);

    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        Event event = entityManager.find(Event.class, id);
        if (event != null) {
            entityManager.remove(event);
        }
    }

    @Override
    public User getUserByMail(String email) {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        for (User user : query.getResultList()) {
            if (Objects.equals(user.getMail(), email)) {
                return user;
            }
        }
        return null;
    }
}
