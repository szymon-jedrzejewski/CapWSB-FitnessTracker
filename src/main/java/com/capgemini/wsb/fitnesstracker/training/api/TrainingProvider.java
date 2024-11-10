package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.List;

public interface TrainingProvider {

    List<TrainingDto> findAllTrainings();

    TrainingDto createTraining(NewTrainingDto newTrainingDto);

    List<TrainingDto> findTrainingsByActivityType(ActivityType activityType);

    List<TrainingDto> findCompletedTrainingsAfterDate(String date);

    List<TrainingDto> findTrainingsByUserId(Long userId);
}
