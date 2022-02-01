package com.chess.chess.controller;

import com.chess.chess.model.ERole;
import com.chess.chess.model.Role;
import com.chess.chess.model.User;
import com.chess.chess.payload.SignupRequest;
import com.chess.chess.repository.RoleRepository;
import com.chess.chess.repository.UserRepository;
import com.chess.chess.repository.User_tournamentRepository;
import com.chess.chess.security.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuthControllerTest {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    User_tournamentRepository user_tournamentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @Test
    void register3() {
        Set<String> strRolesBfr = new HashSet<String>();
        strRolesBfr.add("user");
        strRolesBfr.add("mod");
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testManualny2");
        signupRequest.setEmail("testManualny@com");
        signupRequest.setName("testManualny2");
        signupRequest.setSurname("testManualny2");
        signupRequest.setBirthday(java.sql.Date.valueOf("2021-12-25"));
        signupRequest.setCity("Lowicz");
        signupRequest.setPassword("123");
        signupRequest.setRole(strRolesBfr);
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(), signupRequest.getName(), signupRequest.getSurname(), signupRequest.getBirthday(), signupRequest.getCity(),
                encoder.encode(signupRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        Assertions.assertTrue(user.getId()>0);
    }


    @Test
    void deleteUser() {
        User user = userRepository.findById(Long.valueOf(1)).get();
        user.setBan(true);
        userRepository.save(user);
        Assertions.assertTrue(user.getBan()==true);
    }

    @Test
    void editRoleTournament() {
        User user = userRepository.findById(Long.valueOf(2)).get();
        Role role = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        Assertions.assertTrue(user.getRoles().contains(role)==true);
    }
}