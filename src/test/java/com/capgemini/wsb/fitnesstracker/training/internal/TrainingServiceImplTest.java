package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class TrainingServiceImplTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        trainingRepository.deleteAll();
    }

    @Test
    void findTrainingsByUserId() {
    }

    @Test
    void findAllTrainings() {
    }

    @Test
    void createTraining() {
    }

    @Test
    void findTrainingsByActivityType() {
    }

    @Test
    void findCompletedTrainingsAfterDate() {
    }

    @Test
    void updateTraining() {
    }
}