package com.example.backend.repositories;

import com.example.backend.models.Challenge;
import com.example.backend.models.Identifiable;
import com.example.backend.models.User;
import com.example.backend.models.UserEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Transactional
public abstract class AbstractEntityRepositoryJpa<E extends Identifiable> implements EntityRepository<E> {
    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> theEntityClass;
    private static final int WORKLOAD = 12;


    public AbstractEntityRepositoryJpa(Class<E> theEntityClass) {
        this.theEntityClass = theEntityClass;
        System.out.println("Created " + this.getClass().getName() + "<" + this.theEntityClass.getSimpleName() + ">");
    }

    @Override
    public List<E> findAll() {
        String jpql = "SELECT e FROM " + theEntityClass.getSimpleName() + " e";
        return entityManager.createQuery(jpql, theEntityClass).getResultList();
    }

    @Override
    public List<E> findByQuery(String jpqlName, Object... params) {
        TypedQuery<E> query = entityManager.createNamedQuery(jpqlName, theEntityClass);

        int i = 1;
        for (Object param : params) {
            query.setParameter(i++, param);
        }

        return query.getResultList();
    }

    @Override
    public E findById(Long id) {
        return entityManager.find(theEntityClass, id);
    }

    @Override
    public E save(E entity) {
        if (entity.getId() == 0) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        E entity = entityManager.find(theEntityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
            return true;
        }
        return false;
    }


    @Override
    public User login(String email, String password) {
        String jpql = "SELECT u FROM User u WHERE u.mail = :email";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);

        List<User> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            User user = resultList.get(0);
            // Check if the provided password matches the hashed password in the database
            if (checkPassword(password, user.getPassword())) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void register(User user) {
        // Hash the password before storing it
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        entityManager.persist(user);
    }

    @Override
    public boolean entityWithEntityExist(String propertyName, Object value) {
        String jpql = "SELECT e FROM " + theEntityClass.getSimpleName() + " e WHERE e." + propertyName + " = :value";
        TypedQuery<E> query = entityManager.createQuery(jpql, theEntityClass);
        query.setParameter("value", value);

        List<E> entities = query.getResultList();

        return !entities.isEmpty();
    }


    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    @Override
    public E findByProperty(String propertyName, Object value) {
        String jpql = "SELECT e FROM " + theEntityClass.getSimpleName() + " e WHERE e." + propertyName + " = :value";
        TypedQuery<E> query = entityManager.createQuery(jpql, theEntityClass);
        query.setParameter("value", value);

        List<E> entities = query.getResultList();

        if (!entities.isEmpty()) {
            return entities.get(0);
        }

        return null;
    }

}
