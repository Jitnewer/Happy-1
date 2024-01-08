package com.example.backend.repositories.user;

import com.example.backend.models.User;
import com.example.backend.repositories.AbstractEntityRepositoryJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("USER.JPA")
@Primary
public class UserRepositoryJpa extends AbstractEntityRepositoryJpa<User> {
    public UserRepositoryJpa() {
        super(User.class);
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public List<User> findByQuery(String jpqlName, Object... params) {
        return super.findByQuery(jpqlName, params);
    }

    @Override
    public User findById(Long id) {
        return super.findById(id);
    }

    @Override
    public User save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }


    @Override
    public boolean entityWithEntityExist(String propertyName, Object value) {
        return super.entityWithEntityExist(propertyName, value);
    }

    @Override
    public User login(String email, String password) {
        return super.login(email, password);
    }

    @Override
    public void register(User user) {
        super.register(user);
    }
}
