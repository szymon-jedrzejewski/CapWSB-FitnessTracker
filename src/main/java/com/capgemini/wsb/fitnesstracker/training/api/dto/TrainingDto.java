package com.capgemini.wsb.fitnesstracker.training.api.dto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
public record TrainingDto(
        Long id,
        Date startTime,
        Date endTime,
        ActivityType activityType,
        Double distance,
        Double averageSpeed
) {}
