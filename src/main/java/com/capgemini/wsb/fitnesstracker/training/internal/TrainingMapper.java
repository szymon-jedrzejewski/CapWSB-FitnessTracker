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
class TrainingMapper {
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
                newTrainingDto.getStartTime(),
                newTrainingDto.getEndTime(),
                newTrainingDto.getActivityType(),
                newTrainingDto.getDistance(),
                newTrainingDto.getAverageSpeed()

        );
    }

    void updateTrainingDtoToEntity(UpdateTrainingDto updateTrainingDto, Training existingTraining) {

        if (updateTrainingDto.getStartTime() != null) {
            existingTraining.setStartTime(updateTrainingDto.getStartTime());
        }
        if (updateTrainingDto.getEndTime() != null) {
            existingTraining.setEndTime(updateTrainingDto.getEndTime());
        }
        if (updateTrainingDto.getActivityType() != null) {
            existingTraining.setActivityType(updateTrainingDto.getActivityType());
        }
        if (updateTrainingDto.getDistance() != null) {
            existingTraining.setDistance(updateTrainingDto.getDistance());
        }
        if (updateTrainingDto.getAverageSpeed() != null) {
            existingTraining.setAverageSpeed(updateTrainingDto.getAverageSpeed());
        }
    }
}
