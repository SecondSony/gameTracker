package ru.commonuser.gameTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.commonuser.gameTracker.entity.Permission;
import ru.commonuser.gameTracker.enums.UserAction;
import ru.commonuser.gameTracker.enums.UserRole;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(value = "SELECT perm.action FROM Permission perm WHERE perm.role=:role")
    List<UserAction> findActionsByRole(@Param("role") UserRole role);

    Permission findByActionAndRole(UserAction action, UserRole role);
}
