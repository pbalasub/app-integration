package com.pbalasub.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import java.io.File;


@Configuration
public class IntegrationConfig {

    @Autowired
    private Transformer transformer;

    @Bean
    public IntegrationFlow integrationFlow(){
        return IntegrationFlows.from(fileReader(), filePoller -> filePoller.poller(Pollers.fixedDelay(500)))
                .transform(transformer, "transform")
                .handle(fileWriter())
                .get();
    }

    @Bean
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler writer = new FileWritingMessageHandler(new File("destination"));
        writer.setExpectReply(false);
        return writer;
    }

    @Bean
    public FileReadingMessageSource fileReader() {
        FileReadingMessageSource fileReadingMessageSource = new FileReadingMessageSource();
        fileReadingMessageSource.setDirectory(new File("source"));
        return fileReadingMessageSource;
    }
}
