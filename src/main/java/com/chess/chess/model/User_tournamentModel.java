package com.chess.chess.model;

import javax.persistence.*;

@Entity
@Table(name = "usertournament_table")
public class User_tournamentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    TournamentModel tournament;

    public User_tournamentModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TournamentModel getTournament() {
        return tournament;
    }

    public void setTournament(TournamentModel tournament) {
        this.tournament = tournament;
    }

    public User_tournamentModel(User user, TournamentModel tournament) {
        this.user = user;
        this.tournament = tournament;
    }
}
