package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;

import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.training.api.dto.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TrainingServiceImpl trainingServiceImpl;

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

    Training newTrainingDtoToEntity(NewTrainingDto newTrainingDto, UserDto userDto) {

        User user = trainingServiceImpl.mapUserDtoToUser(userDto);

        return new Training(
                user,
                newTrainingDto.startTime(),
                newTrainingDto.endTime(),
                newTrainingDto.activityType(),
                newTrainingDto.distance(),
                newTrainingDto.averageSpeed()

        );
    }

    Training updateTrainingDtoToEntity(UpdateTrainingDto updateTrainingDto, Training existingTraining) {
        if (updateTrainingDto.user() != null) {
            existingTraining.setUser(updateTrainingDto.user());
        }
        if (updateTrainingDto.startTime() != null) {
            existingTraining.setStartTime(updateTrainingDto.startTime());
        }
        if (updateTrainingDto.endTime() != null) {
            existingTraining.setEndTime(updateTrainingDto.endTime());
        }
        if (updateTrainingDto.activityType() != null) {
            existingTraining.setActivityType(updateTrainingDto.activityType());
        }
        if (updateTrainingDto.distance() != null) {
            existingTraining.setDistance(updateTrainingDto.distance());
        }
        if (updateTrainingDto.averageSpeed() != null) {
            existingTraining.setAverageSpeed(updateTrainingDto.averageSpeed());
        }

        return existingTraining;
    }
}
