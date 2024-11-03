package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.training.api.dto.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    TrainingDto toTrainingDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()

        );
    }

    Training newTrainingDtoToEntity(NewTrainingDto newTrainingDto) {

        User user = userService.mapUserDtoToUser(newTrainingDto.userDto());

        return new Training(
                user,
                newTrainingDto.startTime(),
                newTrainingDto.endTime(),
                newTrainingDto.activityType(),
                newTrainingDto.distance(),
                newTrainingDto.averageSpeed()
        );
    }
}
