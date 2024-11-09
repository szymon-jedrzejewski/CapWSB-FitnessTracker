package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.dto.*;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final UserService userService;
    private final TrainingServiceImpl trainingService;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> findAllTrainings() {
        return ResponseEntity.ok(trainingService.findAllTrainings());
    }

    @GetMapping("/user")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUser(@RequestParam Long userid) {
        UserDto user = userService.findUserById(userid);
        List<TrainingDto> trainings = trainingService.findTrainingsByUser(user);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUserId(@PathVariable Long userId) {
        List<TrainingDto> trainings = trainingService.getTrainingsByUserId(userId);
        return ResponseEntity.ok(trainings);
    }


    @GetMapping("/finished/{date}")
    public ResponseEntity<List<TrainingDto>> getCompletedTrainingsAfterDate(@PathVariable("date") String dateStr) {
        try {
            Date date = DateParserUtil.parseDate(dateStr);
            List<TrainingDto> completedTrainings = trainingService.findCompletedTrainingsAfterDate(date);
            return ResponseEntity.ok(completedTrainings);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/activity")
    public ResponseEntity <List<TrainingDto>> findTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return ResponseEntity.ok(trainingService.findTrainingsByActivityType(activityType));
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
