package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByActivityType(ActivityType activityType);

    List<Training> findByUserId(Long userId);

    @Query("SELECT COUNT(t) FROM Training t WHERE t.user.id = :userId AND MONTH(t.startTime) = MONTH(CURRENT_DATE) AND YEAR(t.startTime) = YEAR(CURRENT_DATE)")
    int countTrainingsForUserInCurrentMonth(@Param("userId") Long userId);
}
