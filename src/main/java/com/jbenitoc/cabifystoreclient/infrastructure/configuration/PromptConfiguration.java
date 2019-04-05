package com.jbenitoc.cabifystoreclient.infrastructure.configuration;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class PromptConfiguration {

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("cabify-store:> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
