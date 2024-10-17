package com.example.demo.batch;

import com.example.demo.service.EmailService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
@EnableScheduling // 스케줄링 활성화
@Primary
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmailService emailService;
    private final JobLauncher jobLauncher;  // JobLauncher 추가

    // 생성자를 통해 필요한 의존성 주입
    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, EmailService emailService, JobLauncher jobLauncher) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.emailService = emailService;
        this.jobLauncher = jobLauncher; // JobLauncher 주입
    }

    @Bean
    public Job emailAndHelloJob(Step emailStep, Step helloStep) {
        return new JobBuilder("emailAndHelloJob", jobRepository)
                .start(emailStep)
                .next(helloStep) // helloStep을 다음 단계로 설정
                .build();
    }

    @Bean
    public Step emailStep() {
        return new StepBuilder("emailStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    // 이메일 전송 로직
                    String to = "zcwxzsx@naver.com"; // 받는 사람 이메일
                    String subject = "정기 이메일";
                    String body = "이 이메일은 정기적으로 전송됩니다.";

                    // 이메일 전송 서비스 호출
                    emailService.sendEmail(to, subject, body);

                    System.out.println("이메일이 전송되었습니다."); // 이메일 전송 완료 로그
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step helloStep() {
        return new StepBuilder("helloStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Hello, Spring Batch!");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    // 스케줄링 메서드 추가
    @Scheduled(cron = "0 52 20 * * ?") // 14:05에 실행되도록 설정 (크론 표현식)
    public void scheduleJob() {
        try {
            jobLauncher.run(emailAndHelloJob(emailStep(), helloStep()), new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());
        } catch (JobExecutionException e) {
            e.printStackTrace();
        }
    }
}
