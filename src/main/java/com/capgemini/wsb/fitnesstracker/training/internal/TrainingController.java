package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.UpdateTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl trainingService;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        return ResponseEntity.ok(trainingService.findAllTrainings());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUserId(@PathVariable Long userId) {
        List<TrainingDto> trainings = trainingService.findTrainingsByUserId(userId);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getFinishedTrainings(@PathVariable String afterTime) {
        return ResponseEntity.ok(trainingService.findCompletedTrainingsAfterDate(afterTime));
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType);
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody NewTrainingDto newTrainingDto) {
        TrainingDto createdTraining = trainingService.createTraining(newTrainingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTraining);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long id, @RequestBody UpdateTrainingDto updateTrainingDto) {
        TrainingDto updatedTraining = trainingService.updateTraining(id, updateTrainingDto);
        return ResponseEntity.ok(updatedTraining);
    }
}
