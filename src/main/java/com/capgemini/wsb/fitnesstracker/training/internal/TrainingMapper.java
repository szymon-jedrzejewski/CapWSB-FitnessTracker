package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.training.api.dto.*;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    TrainingDto toTrainingDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()

        );
    }

    Training newTrainingDtoToEntity(NewTrainingDto newTrainingDto) {
        return new Training(
                newTrainingDto.user(),
                newTrainingDto.startTime(),
                newTrainingDto.endTime(),
                newTrainingDto.activityType(),
                newTrainingDto.distance(),
                newTrainingDto.averageSpeed()
        );
    }

    Training updateTrainingDtoToEntity(UpdateTrainingDto updateTrainingDto) {
        return new Training(
                updateTrainingDto.user(),
                updateTrainingDto.startTime(),
                updateTrainingDto.endTime(),
                updateTrainingDto.activityType(),
                updateTrainingDto.distance(),
                updateTrainingDto.averageSpeed()
        );
    }
}
