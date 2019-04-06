package com.jbenitoc.cabifystoreclient.infrastructure.util;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

public final class ShellFormatter {

    private ShellFormatter() {
    }

    public static String errorMessage(String message) {
        return (new AttributedString(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))).toAnsi();
    }

    public static String successMessage(String message) {
        return (new AttributedString(message, AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN))).toAnsi();
    }

}
