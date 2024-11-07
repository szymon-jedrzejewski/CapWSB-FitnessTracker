package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.*;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
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
    private final UserMapper userMapper;
    private final TrainingRepository trainingRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Override
    public Optional<Training> findTrainingsById(final Long trainingId) {
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
    public List<Training> findTrainingsByUser(Long userId) {
        UserDto user = userService.findUserById(userId);
        return trainingRepository.findByUser(user);
    }

    @Override
    public Training createTraining(Training training) {
        return null;
    }

    @Override
    public List<TrainingDto> findTrainingsByActivityType(ActivityType activityType) {
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
