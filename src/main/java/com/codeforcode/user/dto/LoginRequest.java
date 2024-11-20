package com.codeforcode.user.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

   @NotNull
   private String userId;

   @NotNull
   private String password;
}