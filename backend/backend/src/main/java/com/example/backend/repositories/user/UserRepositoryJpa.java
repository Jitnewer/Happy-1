package com.example.backend.repositories.user;

import com.example.backend.models.Event;
import com.example.backend.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;
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

    private static final int WORKLOAD = 12;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsers() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);

    }

    @Override
    @Transactional
    public void addUser(User user) {
        entityManager.persist(user);
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
    @Transactional
    public void register(User user) {
        // Hash the password before storing it
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        entityManager.persist(user);
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
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

    @Override
    public boolean userWithMailExists(String email) {
        String jpql = "SELECT u FROM User u WHERE u.mail = ?1";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter(1, email);

        List<User> users = query.getResultList();

        for (User user : users) {
            if (user.getMail() == email) {
                return true;
            }
        }
        return false;
    }


    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
