package com.josefigueredo.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
class AnnotationUsageService {

    private static Logger LOG = LoggerFactory.getLogger(AnnotationUsageService.class);

    @Retryable(value = { IOException.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 500))
    public void service() throws IOException {
        LOG.info("Trying...");
        UrlManager.getContent("http://exampleee.com/foo/bar");
    }

    @Recover
    public void recover(IOException e) {
        LOG.info("Recovering from " + e.getMessage());
    }

}