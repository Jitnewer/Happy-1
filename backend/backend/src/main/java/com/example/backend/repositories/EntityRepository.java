package com.example.backend.repositories;


import com.example.backend.models.Identifiable;
import com.example.backend.models.User;

import java.util.List;

public interface EntityRepository<E extends Identifiable> {

    List<E> findAll();
    List <E> findByQuery(String jpqlName, Object... params);
    E findById(Long id);
    E save(E entity);
    boolean deleteById(Long id);

    User login(String email, String password);

    void register(User user);


    boolean entityWithEntityExist(String propertyName, Object value);

    E findByProperty(String propertyName, Object value);

    List<E> findMultipleByProperty(String propertyName, Object value);
}

