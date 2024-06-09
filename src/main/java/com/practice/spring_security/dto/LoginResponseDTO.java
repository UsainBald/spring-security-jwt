package com.practice.spring_security.dto;

import com.practice.spring_security.models.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

  private ApplicationUser user;

  private String jwt;

}
