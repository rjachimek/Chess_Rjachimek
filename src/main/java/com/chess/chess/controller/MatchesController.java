package com.chess.chess.controller;

import com.chess.chess.model.MatchModel;
import com.chess.chess.model.MatchesModel;
import com.chess.chess.model.TournamentModel;
import com.chess.chess.model.User_tournamentModel;
import com.chess.chess.repository.UserRepository;
import com.chess.chess.service.MatchServiceInt;
import com.chess.chess.service.MatchesServiceInt;
import com.chess.chess.service.TournamentServiceInt;
import com.chess.chess.service.User_tournamentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller

public class MatchesController {

    @Autowired
    private MatchesServiceInt matchesServiceInt;

    @Autowired
    private User_tournamentServiceInt user_tournamentServiceInt;

    @Autowired
    private TournamentServiceInt tournamentServiceInt;

    @Autowired
    UserRepository userRepository;



    @GetMapping("/matchTournaments/{id}")
    public  String viewMatchPage(@PathVariable(value = "id") int id,Model model){
        List<MatchesModel> list = matchesServiceInt.getAllMatches();
        List<MatchesModel> list2 = new ArrayList<MatchesModel>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_tournamentModel().getTournament().getId()==id){
                list2.add(list.get(i));
            }

        }
        model.addAttribute("getAllMatches",list2);

        return  "matchTournaments";
    }


    @GetMapping("/showNewMatchForm2")
    public String  showNewMatchPage(Model model){
        MatchesModel matchModel = new MatchesModel();
        model.addAttribute("matchModel", matchModel);
        return "showNewMatchForm2";
    }

    @PostMapping("/saveMatch")
    public String saveMatch(@ModelAttribute("matchModel") MatchesModel matchModel){

        matchesServiceInt.saveMatch(matchModel);
        return "redirect:/index";
    }


    @GetMapping("/showNewMatchForm/{id}")
    public  String Match(@PathVariable(value = "id") int id, Model model){
        System.out.println(id);
        List<MatchesModel> matchModels = new ArrayList<MatchesModel>();
        List<User_tournamentModel> user_tournamentModelList = new ArrayList<User_tournamentModel>();
        int users=0;
        int models=0;
        System.out.println(user_tournamentServiceInt.getAllUserTournaments().size());
        for (int i = 0; i < user_tournamentServiceInt.getAllUserTournaments().size(); i++) {
            if (user_tournamentServiceInt.getAllUserTournaments().get(i).getTournament().getId()==id){
                if (!user_tournamentModelList.contains(user_tournamentServiceInt.getAllUserTournaments().get(i)))
                {
                    user_tournamentModelList.add(user_tournamentServiceInt.getAllUserTournaments().get(i));
                    if (users==0){
                        MatchesModel matchModel = new MatchesModel();
                        matchModels.add(matchModel);
                        matchModels.get(models).setUser_tournamentModel(user_tournamentServiceInt.getAllUserTournaments().get(i));
                    }
                    if (users==1){matchModels.get(models).setUser_tournamentModel2(user_tournamentServiceInt.getAllUserTournaments().get(i));}
                    users++;
                }
            }

            if (users==2){
                users=0;
                System.out.println("zapis1:" +matchModels.get(models).getUser_tournamentModel().getId());
                System.out.println("zapis2:" +matchModels.get(models).getUser_tournamentModel2().getId());
                    matchesServiceInt.saveMatch(matchModels.get(models));
                models++;
            }
        }

        return "redirect:/index";
    }



}
