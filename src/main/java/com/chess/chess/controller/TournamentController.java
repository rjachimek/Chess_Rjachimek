package com.chess.chess.controller;

import com.chess.chess.model.TournamentModel;
import com.chess.chess.model.User;
import com.chess.chess.service.TournamentServiceInt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class TournamentController {

    @Autowired
    private TournamentServiceInt tournamentServiceInt;

    @GetMapping("/index")
    public  String viewHomePage(Model model){
        model.addAttribute("listTournaments",tournamentServiceInt.getAllTournaments());
        return  "index";
    }

    @GetMapping("/showNewTournamentForm")
    public String  showNewTournamentForm(Model model){
        TournamentModel tournamentModel = new TournamentModel();
        model.addAttribute("tournamentModel", tournamentModel);
        return "new_tournament";
    }

    @PostMapping("/saveTournament")
    public String saveTournament(@ModelAttribute("tournamentModel") TournamentModel tournamentModel){
       tournamentServiceInt.saveTournament(tournamentModel);
       return "redirect:/index";
    }

    @GetMapping("/showFormUpdate/{id}")
    public  String showFormUpdate(@PathVariable(value = "id") int id, Model model){
        TournamentModel tournamentModel = tournamentServiceInt.getTournamentById(id);
        model.addAttribute("tournamentModel", tournamentModel);
        return "update_tournament";
    }

    @GetMapping("/deleteTournament/{id}")
    public String deleteTournament(@PathVariable(value = "id") int id, Model model){
        this.tournamentServiceInt.deleteTournamentById(id);
        return "redirect:/index";

    }

}
