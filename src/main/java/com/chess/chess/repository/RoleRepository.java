package com.chess.chess.repository;

import java.util.Optional;

import com.chess.chess.model.ERole;
import com.chess.chess.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}