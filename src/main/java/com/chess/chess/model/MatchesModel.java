package com.chess.chess.model;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "matches_table")
public class MatchesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer match_id;

    String result;

    Date start_date;

    @ManyToOne
    @JoinColumn(name="user_tournament_id")
    User_tournamentModel user_tournamentModel;

    @ManyToOne
    @JoinColumn(name="user_tournament_id2")
    User_tournamentModel user_tournamentModel2;


    public Integer getMatch_id() {
        return match_id;
    }

    public void setMatch_id(Integer match_id) {
        this.match_id = match_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public User_tournamentModel getUser_tournamentModel() {
        return user_tournamentModel;
    }

    public void setUser_tournamentModel(User_tournamentModel user_tournamentModel) {
        this.user_tournamentModel = user_tournamentModel;
    }

    public User_tournamentModel getUser_tournamentModel2() {
        return user_tournamentModel2;
    }

    public void setUser_tournamentModel2(User_tournamentModel user_tournamentModel2) {
        this.user_tournamentModel2 = user_tournamentModel2;
    }
}


