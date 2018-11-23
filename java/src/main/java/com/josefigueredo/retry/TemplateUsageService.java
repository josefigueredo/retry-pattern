package com.josefigueredo.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TemplateUsageService {

    private static Logger LOG = LoggerFactory.getLogger(TemplateUsageService.class);

    @Autowired
    private RetryTemplate retryTemplate;

    public void withTemplate() throws IOException {
        retryTemplate.execute(
                context -> {
                    LOG.info("Trying... #" + context.getRetryCount() );
                    UrlManager.getContent("http://exampleee.com/foo/bar");
                    return null;
                },
                context -> {
                    LOG.info("Recovering after " + context.getRetryCount() + " tryies from " + context.getLastThrowable().getMessage());
                    return null;
                }
        );
    }

}
