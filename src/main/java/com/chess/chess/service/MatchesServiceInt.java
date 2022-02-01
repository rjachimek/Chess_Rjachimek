package com.chess.chess.service;

import com.chess.chess.model.MatchModel;
import com.chess.chess.model.MatchesModel;

import java.util.List;

public interface MatchesServiceInt {
    List<MatchesModel> getAllMatches();
    void saveMatch(MatchesModel matchModel);
    MatchesModel getMatchById(int id);
    void  deleteMatchById(int id);
}
