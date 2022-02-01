package com.chess.chess.repository;


import com.chess.chess.model.User_tournamentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_tournamentRepository extends JpaRepository<User_tournamentModel, Integer > {
}
