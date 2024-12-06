package com.blogapp12.controller;

import com.blogapp12.entity.User;
import com.blogapp12.payload.LoginDto;
import com.blogapp12.payload.Signup;
import com.blogapp12.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //http://localhost:8080/api/auth/sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody Signup signUp){

    if(userRepository.existsByEmail(signUp.getEmail())){
        return new ResponseEntity<>("email is already registered", HttpStatus.INTERNAL_SERVER_ERROR);
    }
        if(userRepository.existsByUsername(signUp.getEmail())){
            return new ResponseEntity<>("user name is already registered", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user=new User();

        user.setName(signUp.getName());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));

        userRepository.save(user);
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);
    }
    @PostMapping("/sign-in")
    public ResponseEntity <String> signIn(@RequestBody LoginDto loginDto) {
        //1.Supply loginDto.getUsername() to loadByUser method in CustomUserDetail class
        //2.It will compare
        //Expected credentials-loginDto.getUsername(),loginDto.getPassword()
        //With actual credentials given by loadByUser method
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //Similar to session variable
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("sign-in successful",HttpStatus.OK);
    }



}
