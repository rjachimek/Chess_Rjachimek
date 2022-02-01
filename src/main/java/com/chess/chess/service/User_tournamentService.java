package com.chess.chess.service;

import com.chess.chess.model.User_tournamentModel;
import com.chess.chess.repository.User_tournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_tournamentService implements User_tournamentServiceInt{

    @Autowired
    private User_tournamentRepository user_tournamentRepository;


    @Override
    public List<User_tournamentModel> getAllUserTournaments()
    {
        return user_tournamentRepository.findAll();
    }

    @Override
    public void saveUserTournament(User_tournamentModel user_tournamentModel) {
        this.user_tournamentRepository.save(user_tournamentModel);
    }

    @Override
    public User_tournamentModel getUserTournamentById(int id) {
        return null;
    }

    @Override
    public void deleteUserTournamentById(int id) {
        this.user_tournamentRepository.deleteById(id);
    }
}
