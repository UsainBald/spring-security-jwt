package com.practice.spring_security.controllers;

import com.practice.spring_security.dto.LoginResponseDTO;
import com.practice.spring_security.dto.RegistrationDTO;
import com.practice.spring_security.models.ApplicationUser;
import com.practice.spring_security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ApplicationUser register(@RequestBody RegistrationDTO registrationDTO) throws RoleNotFoundException {
    return authService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword());
  }

  @PostMapping("/login")
  public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
    return authService.loginUser(body.getUsername(), body.getPassword());
  }
}
