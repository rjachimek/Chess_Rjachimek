package com.chess.chess.service;

import com.chess.chess.model.MatchModel;
import com.chess.chess.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService  implements MatchServiceInt{
    @Autowired
    private MatchRepository matchRepository;

    @Override
    public List<MatchModel> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public void saveMatch(MatchModel matchModel) {
        this.matchRepository.save(matchModel);
    }

    @Override
    public MatchModel getMatchById(int id) {
        return null;
    }

    @Override
    public void deleteMatchById(int id) {

    }
}
