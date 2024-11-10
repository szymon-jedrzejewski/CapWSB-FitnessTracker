package com.capgemini.wsb.fitnesstracker.training.api.dto;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.Data;

import java.util.Date;

@Data
public class NewTrainingDto {
    private final Long id;
    private final Long userId;
    private final Date startTime;
    private final  Date endTime;
    private final ActivityType activityType;
    private final Double distance;
    private final  Double averageSpeed;
}
