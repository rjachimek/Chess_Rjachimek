package com.chess.chess.repository;

import com.chess.chess.model.MatchModel;
import com.chess.chess.model.MatchesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchesRepository extends JpaRepository<MatchesModel, Integer > {
}
