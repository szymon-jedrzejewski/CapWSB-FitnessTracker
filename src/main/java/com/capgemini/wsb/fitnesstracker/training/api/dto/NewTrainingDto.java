package com.capgemini.wsb.fitnesstracker.training.api.dto;
import java.util.Date;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
public record NewTrainingDto(
        Long id,
        User user,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}
