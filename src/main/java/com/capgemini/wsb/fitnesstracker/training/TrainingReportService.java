package com.capgemini.wsb.fitnesstracker.training;
import com.capgemini.wsb.fitnesstracker.mail.EmailService;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import com.capgemini.wsb.fitnesstracker.user.api.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingReportService {

    private final TrainingRepository trainingRepository;
    private final UserService userService;
    private final EmailService emailService;

    public TrainingReportService(TrainingRepository trainingRepository, UserService userService, EmailService emailService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    public void generateAndSendMonthlyReports() {
        List<UserDto> users = userService.findAllUsers();

        for (UserDto user : users) {
            int trainingCount = trainingRepository.countTrainingsForUserInCurrentMonth(user.id());
            String report = String.format("Podsumowanie treningów: W tym miesiącu masz zarejestrowanych %d treningów.", trainingCount);

            emailService.sendEmail(user.email(), "Miesięczne podsumowanie treningów", report);
        }
    }
}
