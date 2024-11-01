package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.*;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Training createTraining(Training training) {
        return null;
    }

    @Override
    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    public List<TrainingDto> getCompletedTrainingsAfterDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateStr);
            return trainingRepository.findAllByEndTimeAfter(date)
                    .stream()
                    .map(trainingMapper::toTrainingDto)
                    .toList();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTrainingById(Long trainingId) {

    }

    @Override
    public TrainingDto createTraining(NewTrainingDto training) {
        return null;
    }

    @Override
    public Training updateTraining(Long id, Training trainingDto) {
        return null;
    }
}
