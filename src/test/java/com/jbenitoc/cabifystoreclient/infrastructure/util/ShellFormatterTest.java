package com.jbenitoc.cabifystoreclient.infrastructure.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShellFormatterTest {

    @Test
    void givenMessage_whenErrorMessage_thenItReturnsARedColoredMessage() {
        char[] expected = {'', '[', '3', '1', 'm', 'M', 'e', 's', 's', 'a', 'g', 'e', '', '[', '0', 'm'};
        assertThat(ShellFormatter.errorMessage("Message").toCharArray()).isEqualTo(expected);
    }

    @Test
    void givenMessage_whenSuccessMessage_thenItReturnsAGreenColoredMessage() {
        char[] expected = {'', '[', '3', '2', 'm', 'M', 'e', 's', 's', 'a', 'g', 'e', '', '[', '0', 'm'};
        assertThat(ShellFormatter.successMessage("Message").toCharArray()).isEqualTo(expected);
    }
}