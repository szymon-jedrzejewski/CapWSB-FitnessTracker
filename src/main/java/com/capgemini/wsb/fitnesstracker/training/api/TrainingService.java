package com.capgemini.wsb.fitnesstracker.training.api;


import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.UpdateTrainingDto;

import java.util.List;

/**
 * Interface (API) for modifying operations on {@link Training} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface TrainingService extends TrainingProvider {

    void deleteTrainingById(Long trainingId);
    TrainingDto createTraining(NewTrainingDto newTrainingDto);
    TrainingDto updateTraining(Long id, UpdateTrainingDto updateTrainingDto);
}
