package com.chess.chess.service;

import com.chess.chess.model.TournamentModel;
import com.chess.chess.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService implements TournamentServiceInt {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public List<TournamentModel> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public void saveTournament(TournamentModel tournamentModel) {
        this.tournamentRepository.save(tournamentModel);

    }
    @Override
    public TournamentModel getTournamentById(int id) {
        Optional<TournamentModel> optional = tournamentRepository.findById(id);
        TournamentModel tournamentModel = null;
        if (optional.isPresent()){
            tournamentModel = optional.get();
        }else {
            throw new RuntimeException("Tournament not found");
        }
        return tournamentModel;
    }

    @Override
    public void deleteTournamentById(int id) {
        this.tournamentRepository.deleteById(id);

    }
}
