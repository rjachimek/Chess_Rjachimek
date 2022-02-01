package com.chess.chess.service;

import com.chess.chess.model.TournamentModel;

import java.util.List;

public interface TournamentServiceInt {
    List<TournamentModel> getAllTournaments();
    void saveTournament(TournamentModel tournamentModel);
    TournamentModel getTournamentById(int id);
    void  deleteTournamentById(int id);
}
