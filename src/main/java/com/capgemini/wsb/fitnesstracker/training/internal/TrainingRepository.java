package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByActivityType(ActivityType activityType);

    List<Training> findByUserId(Long userId);

}
