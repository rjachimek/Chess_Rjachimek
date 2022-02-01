package com.chess.chess.controller;


import com.chess.chess.model.TournamentModel;
import com.chess.chess.model.User;
import com.chess.chess.model.User_tournamentModel;
import com.chess.chess.repository.UserRepository;
import com.chess.chess.service.TournamentServiceInt;
import com.chess.chess.service.UserDetailsImpl;
import com.chess.chess.service.User_tournamentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class User_tournamentController {

    @Autowired
    private User_tournamentServiceInt user_tournamentServiceInt;

    @Autowired
    private TournamentServiceInt tournamentServiceInt;

    @Autowired
    UserRepository userRepository;




    @GetMapping("/userTournaments")
    public  String viewUserTournamentPage(Model model){
        List<User_tournamentModel> list = user_tournamentServiceInt.getAllUserTournaments();
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ArrayList<User_tournamentModel> list2 = new ArrayList<User_tournamentModel>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getUser().getId()==userDetails.getId()){
                list2.add(list.get(i));
            }
        }
        model.addAttribute("listUserTournaments",list2);

        return  "userTournaments";
    }

    @GetMapping("/userTournamentsErr")
    public  String viewUserTournamentPageErr(Model model){
        List<User_tournamentModel> list = user_tournamentServiceInt.getAllUserTournaments();
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ArrayList<User_tournamentModel> list2 = new ArrayList<User_tournamentModel>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getUser().getId()==userDetails.getId()){
                list2.add(list.get(i));
            }
        }
        model.addAttribute("listUserTournaments",list2);

        return  "userTournamentsErr";
    }

    @GetMapping("/userTournamentsId/{id}")
    public  String viewUserTournamentPageId(@PathVariable(value = "id") int id, Model model){
        User_tournamentModel user_tournamentModel= user_tournamentServiceInt.getUserTournamentById(id);
        List<User_tournamentModel> list = user_tournamentServiceInt.getAllUserTournaments();
        for (int i = 0; i < list.size(); i++) {

               if (list.get(i).getId()==id){
                   user_tournamentModel=list.get(i);
               }

        }
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //ArrayList<User_tournamentModel> list2 = new ArrayList<User_tournamentModel>();
        //list2.add(user_tournamentModel);
        model.addAttribute("UserTournament",user_tournamentModel);

        return  "userTournamentsId";
    }

    @GetMapping("/showNewUserTournamentForm/{id}")
    public String  showNewUserTournamentForm(@PathVariable(value = "id") int id, Model model){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        TournamentModel tournamentModel = tournamentServiceInt.getTournamentById(id);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getEmail());
        User_tournamentModel user_tournamentModel = new User_tournamentModel(user, tournamentModel);

        model.addAttribute("user_tournamentModel", user_tournamentModel);
        return "new_userTournament";
    }

    @PostMapping("/saveUserTournament")
    public String saveUserTournament(@ModelAttribute("user_tournamentModel") User_tournamentModel user_tournamentModel){
        int players = 0;
        for (int i = 0; i < user_tournamentServiceInt.getAllUserTournaments().size(); i++) {
            int j = user_tournamentServiceInt.getAllUserTournaments().get(i).getTournament().getId();
            long z = user_tournamentServiceInt.getAllUserTournaments().get(i).getUser().getId();

            if (user_tournamentModel.getUser().getId()==z){
                if (user_tournamentModel.getTournament().getId()==j){
                    return "/userTournamentsErr";
                }
            }

        }
        for (int i = 0; i < user_tournamentServiceInt.getAllUserTournaments().size(); i++) {

            if (user_tournamentModel.getTournament() == user_tournamentServiceInt.getAllUserTournaments().get(i).getTournament()){
                players++;
            }
        }

        if (players>user_tournamentModel.getTournament().getMax_players())
        {
            return "/userTournamentsErr";
        }
        user_tournamentServiceInt.saveUserTournament(user_tournamentModel);
        return "redirect:/userTournaments";
    }

    @GetMapping("/deleteUserTournament/{id}")
    public String deleteUserTournament(@PathVariable(value = "id") int id, Model model){
        this.user_tournamentServiceInt.deleteUserTournamentById(id);
        return "redirect:/userTournaments";

    }
}
