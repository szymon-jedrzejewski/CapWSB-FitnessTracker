package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;

import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.training.api.dto.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserMapper userMapper;
    TrainingDto toTrainingDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed(),
                training.getUser() != null ? userMapper.toUserDto(training.getUser()) : null
                //userMapper.toUserDto(training.getUser())

        );
    }

    Training newTrainingDtoToEntity(NewTrainingDto newTrainingDto) {
        if (newTrainingDto.user() == null){
            throw new IllegalArgumentException("User cannot be null when creating a training.");
        }
        return new Training(
                newTrainingDto.user(),
                newTrainingDto.startTime(),
                newTrainingDto.endTime(),
                newTrainingDto.activityType(),
                newTrainingDto.distance(),
                newTrainingDto.averageSpeed()

        );
    }

    Training updateTrainingDtoToEntity(UpdateTrainingDto updateTrainingDto, Training existingTraining) {
        if (updateTrainingDto.userDto() != null) {
            User user = userMapper.mapUserDtoToUser(updateTrainingDto.userDto());
            existingTraining.setUser(user);
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
