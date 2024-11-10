package com.capgemini.wsb.fitnesstracker.training.api.dto;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateTrainingDto {
    private final Long id;
    private final UserDto userDto;
    private final Date startTime;
    private final Date endTime;
    private final ActivityType activityType;
    private final Double distance;
    private final  Double averageSpeed;
}