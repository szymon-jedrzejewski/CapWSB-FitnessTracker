package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.mail.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void shouldSendEmailSuccessfully() {

        String to = "example@example.com";
        String subject = "Test Email";
        String text = "This is a test email sent from the EmailService test.";

        assertDoesNotThrow(() -> emailService.sendEmail(to, subject, text));
        }
    }