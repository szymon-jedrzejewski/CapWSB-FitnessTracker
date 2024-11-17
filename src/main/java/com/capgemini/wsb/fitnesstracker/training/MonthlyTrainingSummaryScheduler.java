package com.capgemini.wsb.fitnesstracker.training;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonthlyTrainingSummaryScheduler {

    private final TrainingReportService trainingReportService;

    public MonthlyTrainingSummaryScheduler(TrainingReportService trainingReportService) {
        this.trainingReportService = trainingReportService;
    }

    @Scheduled(cron = "0 0 8 1 * ?")
    public void generateAndSendMonthlyReport() {
        trainingReportService.generateAndSendMonthlyReports();
    }
}
