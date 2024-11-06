package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.UpdateTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.*;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    private final TrainingMapper trainingMapper;
    private final TrainingRepository trainingRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<Training> getTrainingById(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<TrainingDto> findAllTrainings() {
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    @Override
    public List<Training> getTrainingsByUser(UserDto user) {
        return trainingRepository.findByUser(user);
    }


    @Override
    public TrainingDto createTraining(NewTrainingDto newTrainingDto) {
        Training training = trainingMapper.newTrainingDtoToEntity(newTrainingDto);
        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toTrainingDto(savedTraining);
    }

    @Override
    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    public List<TrainingDto> findCompletedTrainingsAfterDate(Date date) {
        return trainingRepository.findAllByEndTimeAfter(date)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    @Override
    public void deleteTrainingById(Long trainingId) {
        trainingRepository.deleteById(trainingId);
    }


    @Override
    public TrainingDto updateTraining(Long id, UpdateTrainingDto updateTrainingDto) {
        Training existingTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));

        trainingMapper.updateTrainingDtoToEntity(updateTrainingDto, existingTraining);
        Training updatedTraining = trainingRepository.save(existingTraining);

        return trainingMapper.toTrainingDto(updatedTraining);
    }
}