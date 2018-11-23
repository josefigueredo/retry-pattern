package com.josefigueredo.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;


@EnableRetry
@SpringBootApplication
public class RetryApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(RetryApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting the App");
        SpringApplication.run(RetryApplication.class, args);
        LOG.info("App finished");
    }

    @Override
    public void run(String... args) {
        LOG.info("Excecuting");

        try {
            annotationUsageService.service();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        try {
            templateUsageService.withTemplate();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    @Autowired
    AnnotationUsageService annotationUsageService;

    @Autowired
    TemplateUsageService templateUsageService;

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(500l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(4);
        retryTemplate.setRetryPolicy(retryPolicy);

        retryTemplate.registerListener(new DefaultListenerSupport());
        return retryTemplate;
    }

}
