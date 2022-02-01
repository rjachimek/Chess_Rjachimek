package com.chess.chess.service;

import com.chess.chess.model.MatchModel;
import com.chess.chess.model.User_tournamentModel;

import java.util.List;

public interface MatchServiceInt {
    List<MatchModel> getAllMatches();
    void saveMatch(MatchModel matchModel);
    MatchModel getMatchById(int id);
    void  deleteMatchById(int id);
}
