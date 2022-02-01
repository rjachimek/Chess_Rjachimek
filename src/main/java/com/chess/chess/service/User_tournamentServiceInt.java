package com.chess.chess.service;

import com.chess.chess.model.User_tournamentModel;

import java.util.List;

public interface User_tournamentServiceInt {
    List<User_tournamentModel> getAllUserTournaments();
    void saveUserTournament(User_tournamentModel user_tournamentModel);
    User_tournamentModel getUserTournamentById(int id);
    void  deleteUserTournamentById(int id);
}
