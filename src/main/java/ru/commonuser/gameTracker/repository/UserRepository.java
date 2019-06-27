package ru.commonuser.gameTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.commonuser.gameTracker.entity.User;
import ru.commonuser.gameTracker.enums.UserRole;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findFirstByLogin(String login);

    Optional<User> findByRole(UserRole role);

    Optional<User> findById(Long id);
}
