package com.jbenitoc.cabifystoreclient.infrastructure.configuration;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CabifyPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("cabify-store:> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
