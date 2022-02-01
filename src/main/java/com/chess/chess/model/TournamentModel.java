package com.chess.chess.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "tournament_table")
public class TournamentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    Integer system_code;

    Integer min_players;

    Integer max_players;

    Integer rounds;

    Date start_date;

    String start_time;

    String address;

    Integer max_age;

    @OneToMany(mappedBy="tournament")
    private Set<MatchModel> matchModels;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSystem_code() {
        return system_code;
    }

    public void setSystem_code(Integer system_code) {
        this.system_code = system_code;
    }

    public Integer getMin_players() {
        return min_players;
    }

    public void setMin_players(Integer min_players) {
        this.min_players = min_players;
    }

    public Integer getMax_players() {
        return max_players;
    }

    public void setMax_players(Integer max_players) {
        this.max_players = max_players;
    }

    public Integer getRounds() {
        return rounds;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMax_age() {
        return max_age;
    }

    public void setMax_age(Integer max_age) {
        this.max_age = max_age;
    }

    public Set<MatchModel> getMatchModels() {
        return matchModels;
    }

    public void setMatchModels(Set<MatchModel> matchModels) {
        this.matchModels = matchModels;
    }
}
