package com.capgemini.wsb.fitnesstracker.user.api.dto;

import jakarta.annotation.Nullable;

public record UserBasicInfoDto(@Nullable Long id, String firstName, String lastName) {

}