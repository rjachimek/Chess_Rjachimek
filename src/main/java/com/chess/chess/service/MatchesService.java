package com.chess.chess.service;

import com.chess.chess.model.MatchModel;
import com.chess.chess.model.MatchesModel;
import com.chess.chess.repository.MatchRepository;
import com.chess.chess.repository.MatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchesService implements MatchesServiceInt{
    @Autowired
    private MatchesRepository matchRepository;

    @Override
    public List<MatchesModel> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public void saveMatch(MatchesModel matchModel) {
        this.matchRepository.save(matchModel);
    }

    @Override
    public MatchesModel getMatchById(int id) {
        return null;
    }

    @Override
    public void deleteMatchById(int id) {

    }
}
