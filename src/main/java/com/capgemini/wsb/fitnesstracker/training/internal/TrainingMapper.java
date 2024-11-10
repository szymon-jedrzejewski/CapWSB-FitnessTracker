package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.UpdateTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    TrainingDto toTrainingDto(Training training, UserDto user) {
        return new TrainingDto(
                training.getId(),
                user,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()

        );
    }

    Training newTrainingDtoToEntity(NewTrainingDto newTrainingDto, User user) {
        return new Training(
                user,
                newTrainingDto.startTime(),
                newTrainingDto.endTime(),
                newTrainingDto.activityType(),
                newTrainingDto.distance(),
                newTrainingDto.averageSpeed()

        );
    }

    void updateTrainingDtoToEntity(UpdateTrainingDto updateTrainingDto, Training existingTraining) {

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
    }
}
