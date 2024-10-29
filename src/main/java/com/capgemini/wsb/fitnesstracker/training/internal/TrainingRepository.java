package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Date;

interface TrainingRepository extends JpaRepository<Training, Long> {
    Optional<Training> getTrainingById(Long trainingId);
    List<Training> findByStartTimeAfter(LocalDate date);
    List<Training> findByActivityType(ActivityType activityType);
    List<Training> findByUser(UserDto user);
    List<Training> findAllByEndTimeAfter(Date date);
}