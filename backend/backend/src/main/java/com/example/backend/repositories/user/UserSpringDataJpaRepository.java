package com.example.backend.repositories.user;

import com.example.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSpringDataJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByMail(String mail);
    Optional<User> findByMailAndPassword(String mail, String password);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.mail = :mail")
    boolean existsByMail(@Param("mail") String mail);
}
