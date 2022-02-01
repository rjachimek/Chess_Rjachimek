package com.chess.chess.model;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "match_table")
public class MatchModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer match_id;

    String result;

    Date start_date;

    @ManyToOne
    @JoinColumn(name="tournament_id")
    TournamentModel tournament;


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

    public TournamentModel getTournament() {
        return tournament;
    }

    public void setTournament(TournamentModel tournament) {
        this.tournament = tournament;
    }
}


