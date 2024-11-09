package com.capgemini.wsb.fitnesstracker.training.api.dto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Date;

public record TrainingDto(
        Long id,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        Double distance,
        Double averageSpeed
) {}
