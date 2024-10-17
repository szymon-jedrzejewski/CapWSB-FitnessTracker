package com.capgemini.wsb.fitnesstracker.user.api.dto;

import jakarta.annotation.Nullable;

public record UserEmailAndIdDto(@Nullable Long id, String email) {

}