package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.api.dto.NewTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.dto.UpdateTrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.NewUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class TrainingServiceImplTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private UserService userService;


    private UserDto testUser;

    @BeforeEach
    void setUp() {
        String uniqueEmail = "tester" + System.currentTimeMillis() + "@example.com";
        NewUserDto newUser = new NewUserDto("Alex", "Dunphy", LocalDate.of(1999,01,04),uniqueEmail, "password", List.of("ADMIN"));


        testUser = userService.createUser(newUser);
    }

    @AfterEach
    void tearDown() {
        trainingRepository.deleteAll();
        userService.deleteUserById(testUser.id());

    }

    @Test
    void findTrainingsByUserId() {
        NewTrainingDto newTraining = new NewTrainingDto(null, testUser.id(), new Date(), new Date(), ActivityType.RUNNING, 5.0, 10.0);
        trainingService.createTraining(newTraining);

        List<TrainingDto> trainings = trainingService.findTrainingsByUserId(testUser.id());
        assertNotNull(trainings);
        assertFalse(trainings.isEmpty());
        assertEquals(1, trainings.size());
        assertEquals(testUser.id(), trainings.get(0).user().id());
    }

    @Test
    void findAllTrainings() {
        NewTrainingDto newTraining1 = new NewTrainingDto(null, testUser.id(), new Date(), new Date(), ActivityType.CYCLING, 10.0, 15.0);
        NewTrainingDto newTraining2 = new NewTrainingDto(null, testUser.id(), new Date(), new Date(), ActivityType.SWIMMING, 2.0, 2.5);

        trainingService.createTraining(newTraining1);
        trainingService.createTraining(newTraining2);

        List<TrainingDto> trainings = trainingService.findAllTrainings();
        assertNotNull(trainings);
        assertEquals(2, trainings.size());
    }

    @Test
    void createTraining() {
        NewTrainingDto newTraining = new NewTrainingDto(null, testUser.id(), new Date(), new Date(), ActivityType.RUNNING, 5.0, 12.0);
        TrainingDto createdTraining = trainingService.createTraining(newTraining);

        assertNotNull(createdTraining);
        assertEquals(newTraining.userId(), createdTraining.user().id());
        assertEquals(newTraining.activityType(), createdTraining.activityType());
        assertEquals(newTraining.distance(), createdTraining.distance());
        assertEquals(newTraining.averageSpeed(), createdTraining.averageSpeed());
    }

    @Test
    void findTrainingsByActivityType() {
        NewTrainingDto newTraining = new NewTrainingDto(null, testUser.id(), new Date(), new Date(), ActivityType.WALKING, 3.0, 4.5);
        trainingService.createTraining(newTraining);

        List<TrainingDto> trainings = trainingService.findTrainingsByActivityType(ActivityType.WALKING);
        assertNotNull(trainings);
        assertFalse(trainings.isEmpty());
        assertEquals(ActivityType.WALKING, trainings.get(0).activityType());
    }

    @Test
    void findCompletedTrainingsAfterDate() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse("2024-11-01");
        Date endDate = dateFormat.parse("2024-11-10");

        NewTrainingDto newTraining = new NewTrainingDto(null, testUser.id(), startDate, endDate, ActivityType.SWIMMING, 3.0, 2.8);
        trainingService.createTraining(newTraining);

        List<TrainingDto> trainings = trainingService.findCompletedTrainingsAfterDate("2024-11-05");
        assertNotNull(trainings);
        assertFalse(trainings.isEmpty());
        assertTrue(trainings.get(0).endTime().after(dateFormat.parse("2024-11-05")));
    }

    @Test
    void updateTraining() {
        NewTrainingDto newTraining = new NewTrainingDto(null, testUser.id(), new Date(), new Date(), ActivityType.RUNNING, 4.0, 9.0);
        TrainingDto createdTraining = trainingService.createTraining(newTraining);

        UpdateTrainingDto updateTrainingDto = new UpdateTrainingDto(
                createdTraining.id(),
                createdTraining.user(),
                createdTraining.startTime(),
                createdTraining.endTime(),
                ActivityType.CYCLING,
                6.0,
                14.0
        );

        TrainingDto updatedTraining = trainingService.updateTraining(createdTraining.id(), updateTrainingDto);

        assertNotNull(updatedTraining);
        assertEquals(ActivityType.CYCLING, updatedTraining.activityType());
        assertEquals(6.0, updatedTraining.distance());
        assertEquals(14.0, updatedTraining.averageSpeed());
    }
}