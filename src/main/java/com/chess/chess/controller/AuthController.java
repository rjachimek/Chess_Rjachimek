package com.chess.chess.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.chess.chess.model.ERole;
import com.chess.chess.model.Role;
import com.chess.chess.model.User;
import com.chess.chess.payload.JwtResponse;
import com.chess.chess.payload.LoginRequest;
import com.chess.chess.payload.MessageResponse;
import com.chess.chess.payload.SignupRequest;
import com.chess.chess.repository.RoleRepository;
import com.chess.chess.repository.UserRepository;
import com.chess.chess.security.jwt.JwtUtils;
import com.chess.chess.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;


@Component
@Scope("session")
@CrossOrigin
@Controller
//@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    Authentication authentication;

    @GetMapping("/login4")
    public String authenticateUserLoginGet(Model model) {

        model.addAttribute("loginRequest", new User());

        return "login4";
    }

    @GetMapping("/login5")
    public String authenticateUserLoginGet5(Model model) {

        model.addAttribute("loginRequest", new User());

        return "login5";
    }

    @PostMapping("/login5")
    public String authenticateUserLogin(@ModelAttribute LoginRequest loginRequest, Model model, HttpServletRequest req) {

        if (userRepository.findByUsername(loginRequest.getUsername()).get().getBan()==true){
            return "redirect:/index";
        }
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        SecurityContext sc = SecurityContextHolder.getContext();
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

         ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));

         return "index";
    }

    @PostMapping("/login4")
    public String authenticateUserLoginPost(@ModelAttribute LoginRequest loginRequest, Model model, HttpServletRequest req) {
        if (userRepository.findByUsername(loginRequest.getUsername()).get().getBan()==true)
        {
            return "redirect:/index";
        }
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authentication = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);
        HttpSession session = req.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);


        return "redirect:/index";
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @GetMapping("/signup")
    public String getRegisterUser(Model model){
        model.addAttribute("signUpRequest", new SignupRequest());
        return "signup";
    }



    @PostMapping("/signup")
    public String registerUser(@ModelAttribute SignupRequest signUpRequest) {
        System.out.println("register request: " + signUpRequest);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            System.out.println("register request: " + signUpRequest);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            System.out.println("register request: " + signUpRequest);
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        System.out.println("register request: " + signUpRequest);
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return "/signup";
    }


    @GetMapping("/register3")
    public String getRegisterUser3(Model model){
        model.addAttribute("signUpRequest2", new User());
        return "register3";
    }

    @PostMapping("/register3")
    public String register3(@ModelAttribute SignupRequest signupRequest ) throws Exception{
        System.out.println("register request: " + signupRequest.getUsername());
        System.out.println("register request: " + signupRequest.getPassword());

        if(userRepository.findByUsername(signupRequest.getUsername()).isPresent()){
            System.out.println("Użytkownik już istnieje");
            return "/registerErrUs";
        }
        if(userRepository.findByUsername(signupRequest.getEmail()).isPresent()){
            System.out.println("Użytkownik już istnieje");
            return "/registerErrUs";
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(), signupRequest.getName(), signupRequest.getSurname(), signupRequest.getBirthday(), signupRequest.getCity(),
                encoder.encode(signupRequest.getPassword()));

        System.out.println("register request: " + signupRequest.getUsername());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        System.out.println(user.getPassword());
        user.setRoles(roles);
        userRepository.save(user);

        return "login4";

    }

    @PostMapping("/registerErrUs")
    public String registerErr(@ModelAttribute SignupRequest signupRequest ) throws Exception{
        System.out.println("register request: " + signupRequest.getUsername());
        System.out.println("register request: " + signupRequest.getPassword());

        if(userRepository.findByUsername(signupRequest.getUsername()).isPresent()){
            System.out.println("Użytkownik już istnieje");
            return "/registerErrUs";
        }
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(), signupRequest.getName(), signupRequest.getSurname(), signupRequest.getBirthday(), signupRequest.getCity(),
                encoder.encode(signupRequest.getPassword()));

        System.out.println("register request: " + signupRequest.getUsername());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return "login4";

    }

    @GetMapping("/signup2")
    public String getRegisterUser2(Model model){
        model.addAttribute("signUpRequest3", new SignupRequest());
        return "register3";
    }

    @GetMapping("/editUser")
    public String getEditUser(@ModelAttribute SignupRequest signupRequest, Model model){

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        SecurityContext sc = SecurityContextHolder.getContext();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        model.addAttribute("userModel", userDetails);

        System.out.println(userDetails);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String postEditUser(@ModelAttribute("userModel") User user){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        userRepository.save(user);
        return "editUser";
    }

    @PostMapping("/signup2")
    public ResponseEntity<?> registerUser2(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/admin")
    public  String viewHomePage(Model model){
        model.addAttribute("listUsers", userRepository.findAll());
        return  "adminPage";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id, Model model){
        User user = userRepository.findById((long) id).get();
        user.setBan(true);
        userRepository.save(user);
        return "redirect:/admin";

    }

    @GetMapping("/editRoleUser/{id}")
    public String editRoleTournament(@PathVariable(value = "id") int id, Model model){
        User user = userRepository.findById((long) id).get();
        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(modRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "redirect:/admin";

    }



}