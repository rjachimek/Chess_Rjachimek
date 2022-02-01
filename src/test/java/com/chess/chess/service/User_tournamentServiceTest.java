package com.chess.chess.service;

import com.chess.chess.model.TournamentModel;
import com.chess.chess.model.User;
import com.chess.chess.model.User_tournamentModel;
import com.chess.chess.repository.TournamentRepository;
import com.chess.chess.repository.UserRepository;
import com.chess.chess.repository.User_tournamentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class User_tournamentServiceTest {

    @Autowired
    private User_tournamentRepository user_tournamentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TournamentRepository tournamentRepository;

    @Autowired
    private TournamentServiceInt tournamentServiceInt;

    @Test
    void saveUserTournament() {

        TournamentModel tournamentModel = tournamentServiceInt.getTournamentById(1);
        User user = userRepository.findByEmail("m@gmail.com");
        User_tournamentModel user_tournamentModel = new User_tournamentModel();
        user_tournamentModel.setUser(user);
        user_tournamentModel.setTournament(tournamentModel);
        user_tournamentRepository.save(user_tournamentModel);

        Assertions.assertTrue(user_tournamentModel.getId()>0);
    }
}