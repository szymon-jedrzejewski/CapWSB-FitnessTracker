package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.dto.*;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final UserService userService;
    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final TrainingRepository trainingRepository;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.findAllTrainings());
    }

    @GetMapping("/user")
    public List<Training> getTrainingsByUser(@RequestParam Long userid) {
        UserDto user = userService.findUserById(userid);
        return trainingService.getTrainingsByUser(user);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUserId(@PathVariable Long userId) {
        UserDto user = userService.findUserById(userId);
        List<TrainingDto> trainings = trainingService.getTrainingsByUser(user).stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TrainingDto>> getCompletedTrainingsAfterDate(@RequestParam("date") String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateStr);
            List<TrainingDto> completedTrainings = trainingService.getCompletedTrainingsAfterDate(date);
            return ResponseEntity.ok(completedTrainings);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType);
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody NewTrainingDto newTrainingDto) {
        TrainingDto createdTraining = trainingService.createTraining(newTrainingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTraining);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long id, @RequestBody UpdateTrainingDto updateTrainingDto) {
        Training training = trainingMapper.updateTrainingDtoToEntity(updateTrainingDto);
        Training updatedTraining = trainingService.updateTraining(id, training);
        return ResponseEntity.ok(trainingMapper.toTrainingDto(updatedTraining));
    }
}
