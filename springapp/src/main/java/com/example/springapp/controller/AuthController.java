package com.example.springapp.controller;

import javax.validation.Valid;

// import javax.print.attribute.standard.Media;
// import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.request.LogInRequest;
import com.example.springapp.request.SignUpRequest;
import com.example.springapp.response.MsgDataResponse;
import com.example.springapp.service.CustomUserDetailsService;
import com.example.springapp.util.JwtUtil;
import com.example.springapp.AccountRoles;
import com.example.springapp.CustomUserDetails;
import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/")
public class AuthController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest sr) {

    User user = new User(sr);

    if (sr.role.equalsIgnoreCase("participant")) {
      user.setRole(AccountRoles.ROLE_USER);
    }

    else if (sr.role.equalsIgnoreCase("organizer")) {
      user.setRole(AccountRoles.ROLE_ORGANISER);
    } else {
      user.setRole(AccountRoles.ROLE_ADMIN);

    }

    user.setPassword(passwordEncoder.encode(sr.password));

    User db_user = userRepository.save(user);

    return new ResponseEntity<>(new MsgDataResponse("Account Created Successfully", db_user), HttpStatus.CREATED);

  }

  @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> signin(@Valid @RequestBody LogInRequest lr) {

    // An AuthenticationManager can do one of 3 things in its authenticate() method:
    // Return an Authentication (normally with authenticated=true ) if it can verify
    // that the input represents a valid principal. Throw an AuthenticationException
    // if it believes that the input represents an invalid principal.

    authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(lr.email, lr.password));

    CustomUserDetails user = customUserDetailsService.loadUserByUsername(lr.email);

    String jwt = jwtUtil.generateToken(user);

    return new ResponseEntity<>(new MsgDataResponse("Logged Successfully", jwt), HttpStatus.OK);

  }
}