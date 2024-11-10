package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.UpdateTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    private final TrainingMapper trainingMapper;
    private final UserMapper userMapper;
    private final TrainingRepository trainingRepository;
    private final UserService userService;

    @Override
    public List<TrainingDto> findTrainingsByUserId(Long userId) {
        UserDto user = userService.findUserById(userId);
        return trainingRepository.findByUserId(userId)
                .stream()
                .map(training -> trainingMapper.toTrainingDto(training, user))
                .toList();
    }

    @Override
    public List<TrainingDto> findAllTrainings() {
        return trainingRepository.findAll()
                .stream()
                .map(training -> trainingMapper.toTrainingDto(training, userMapper.toUserDto(training.getUser())))
                .toList();
    }

    @Transactional
    @Override
    public TrainingDto createTraining(NewTrainingDto newTrainingDto) {
        User user = userService.findUserByIdForTraining(newTrainingDto.userId());
        Training training = trainingMapper.newTrainingDtoToEntity(newTrainingDto, user);

        return trainingMapper.toTrainingDto(trainingRepository.save(training), userMapper.toUserDto(training.getUser()));
    }

    @Override
    public List<TrainingDto> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(training -> trainingMapper.toTrainingDto(training, userMapper.toUserDto(training.getUser())))
                .toList();
    }

    @SneakyThrows
    public List<TrainingDto> findCompletedTrainingsAfterDate(String date) {
        Date parsedDate = DateParserUtil.parseDate(date);
        return trainingRepository.findAll()
                .stream()
                .filter(training -> training.getEndTime().after(parsedDate))
                .map(training -> trainingMapper.toTrainingDto(training, userMapper.toUserDto(training.getUser())))
                .toList();
    }

    @Override
    public TrainingDto updateTraining(Long id, UpdateTrainingDto updateTrainingDto) {
        Training existingTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException(id));

        trainingMapper.updateTrainingDtoToEntity(updateTrainingDto, existingTraining);
        Training updatedTraining = trainingRepository.save(existingTraining);

        return trainingMapper.toTrainingDto(updatedTraining, userMapper.toUserDto(updatedTraining.getUser()));
    }
}
