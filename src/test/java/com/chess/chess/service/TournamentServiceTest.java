package com.chess.chess.service;

import com.chess.chess.model.TournamentModel;
import com.chess.chess.repository.TournamentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TournamentServiceTest {

    @Autowired
    TournamentRepository tournamentRepository;

    @Test
    void getAllTournaments() {
    }

    @Test
    void saveTournament() {

        TournamentService tournamentService = new TournamentService();

        TournamentModel tournamentModel = new TournamentModel();
        tournamentModel.setName("Test manualny");
        tournamentModel.setMin_players(1);
        tournamentModel.setMax_players(2);
        tournamentModel.setRounds(3);
        tournamentModel.setStart_date(java.sql.Date.valueOf("2021-12-25"));
        tournamentModel.setStart_time("10:00");
        tournamentModel.setAddress("Test manualny Adres");
        tournamentModel.setSystem_code(1);
        tournamentModel.setMax_age(10);
        tournamentRepository.save(tournamentModel);

        Assertions.assertTrue(tournamentModel.getId()>0);
    }

    @Test
    void getTournamentById() {
        TournamentModel tournamentModel = new TournamentModel();
        tournamentModel = tournamentRepository.getById(1);
        Assertions.assertTrue(tournamentModel.getId()==1);
    }

    @Test
    void deleteTournamentById() {
    }
}