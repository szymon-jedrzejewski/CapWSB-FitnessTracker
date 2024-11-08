package com.capgemini.wsb.fitnesstracker.training.api.dto;
import java.util.Date;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

public record NewTrainingDto(
        Long id,
        UserDto userDto,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        Double distance,
        Double averageSpeed
) {
}
