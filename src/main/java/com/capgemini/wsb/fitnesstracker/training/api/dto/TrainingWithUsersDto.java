package com.capgemini.wsb.fitnesstracker.training.api.dto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.Date;

public record TrainingWithUsersDto(
        Long id,
        User user,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        Double distance,
        Double averageSpeed
) {}
