package com.practice.spring_security;

import com.practice.spring_security.models.ApplicationUser;
import com.practice.spring_security.models.Role;
import com.practice.spring_security.repositories.RoleRepository;
import com.practice.spring_security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityApplication.class, args);
  }

  @Bean
  public CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return args -> {
      if (roleRepository.findByAuthority("ADMIN").isPresent()) {
        return;
      }

      roleRepository.save(new Role("USER"));
      Role admin = roleRepository.save(new Role("ADMIN"));

      Set<Role> roles = new HashSet<>();
      roles.add(admin);

      ApplicationUser adminUser = new ApplicationUser(1, "admin", "password", roles);
    };
  }

}
