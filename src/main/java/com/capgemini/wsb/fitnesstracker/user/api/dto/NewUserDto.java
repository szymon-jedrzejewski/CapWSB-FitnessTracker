package com.capgemini.wsb.fitnesstracker.user.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record NewUserDto(String firstName, String lastName,
                         @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
                         String email, String password, List<String> roles) {


}
