package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.dto.*;
import com.capgemini.wsb.fitnesstracker.training.api.Training;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/user")
    public ResponseEntity<List<Training>> getTrainingsByUser(@RequestParam Long userId) {
        return ResponseEntity.ok(trainingService.findTrainingsByUser(userId));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TrainingDto>> getCompletedTrainingsAfterDate(@RequestParam("date") String dateStr) {
        List<TrainingDto> completedTrainings = trainingService.getCompletedTrainingsAfterDate(dateStr);
        return ResponseEntity.ok(completedTrainings);
    }

    @GetMapping("/activity")
    public ResponseEntity<List<TrainingDto>> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return ResponseEntity.ok(trainingService.findTrainingsByActivityType(activityType));
    }

}
