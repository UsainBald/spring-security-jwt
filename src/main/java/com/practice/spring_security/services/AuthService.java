package com.practice.spring_security.services;

import com.practice.spring_security.dto.LoginResponseDTO;
import com.practice.spring_security.models.ApplicationUser;
import com.practice.spring_security.models.Role;
import com.practice.spring_security.repositories.RoleRepository;
import com.practice.spring_security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final TokenService tokenService;

  public ApplicationUser registerUser(String username, String password) throws RoleNotFoundException {
    String encodedPassword = passwordEncoder.encode(password);
    Role userRole = roleRepository.findByAuthority("USER").orElseThrow(RoleNotFoundException::new);

    Set<Role> roles = new HashSet<>();
    roles.add(userRole);

    return userRepository.save(new ApplicationUser(0, username, encodedPassword, roles));
  }

  public LoginResponseDTO loginUser(String username, String password) throws UsernameNotFoundException {
    try {
      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password)
      );

      String token = tokenService.generateJwtToken(auth);

      return new LoginResponseDTO(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("")), token);

    } catch (AuthenticationException e) {
      return new LoginResponseDTO(null, "Authentication failed");
    }
  }

}
