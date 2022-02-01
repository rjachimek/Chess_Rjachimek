package com.chess.chess.repository;

import com.chess.chess.model.MatchModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchModel, Integer > {
}
