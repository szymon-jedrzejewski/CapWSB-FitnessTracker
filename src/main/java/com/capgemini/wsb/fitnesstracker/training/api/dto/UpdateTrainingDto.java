package com.capgemini.wsb.fitnesstracker.training.api.dto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Date;

public record UpdateTrainingDto(
        Long id,
        Long userId,
        User user,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        Double distance,
        Double averageSpeed
) {}