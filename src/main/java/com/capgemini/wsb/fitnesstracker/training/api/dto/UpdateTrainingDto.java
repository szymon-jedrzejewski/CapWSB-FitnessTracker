package com.capgemini.wsb.fitnesstracker.training.api.dto;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;

import java.util.Date;

public record UpdateTrainingDto(
        Long id,
        UserDto userDto,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        Double distance,
        Double averageSpeed
) {
}