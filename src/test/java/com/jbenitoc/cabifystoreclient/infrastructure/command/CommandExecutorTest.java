package com.jbenitoc.cabifystoreclient.infrastructure.command;

import com.jbenitoc.cabifystoreclient.domain.model.DomainError;
import com.jbenitoc.cabifystoreclient.domain.model.UnexpectedError;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommandExecutorTest {

    private CommandExecutor executor = new CommandExecutor();

    @Test
    void givenCommand_whenExecute_thenTheCommandIsExecutedAndItResultReturned() {
        final String expectedResult = "It works";
        Command<String> cmd = () -> expectedResult;

        String result = executor.execute(cmd);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void givenCommand_whenDomainErrorOccursDuringExecution_thenTheDomainErrorIsRethrown() {
        DomainError expectedError = new DomainError("A domain error");
        Command cmd = () -> {
            throw expectedError;
        };

        Exception e = assertThrows(DomainError.class, () -> executor.execute(cmd));

        assertThat(e).isEqualTo(expectedError);
    }

    @Test
    void givenCommand_whenResourceAccessExceptionIsThrownDuringExecution_thenAnUnexpectedErrorWithProperMessageAndCauseIsThrown() {
        ResourceAccessException resourceAccessException = new ResourceAccessException("A resource access error");
        Command cmd = () -> {
            throw resourceAccessException;
        };

        Exception e = assertThrows(UnexpectedError.class, () -> executor.execute(cmd));

        assertThat(e.getMessage()).isEqualTo("ERROR: It was impossible to connect to server.");
        assertThat(e.getCause()).isEqualTo(resourceAccessException);
    }

    @Test
    void givenCommand_whenWhateverUncontrolledErrorOccursDuringExecution_thenAnUnexpectedErrorWithProperMessageAndCauseIsThrown() {
        RuntimeException whateverError = new RuntimeException("Whatever uncontrolled error");
        Command cmd = () -> {
            throw whateverError;
        };

        Exception e = assertThrows(UnexpectedError.class, () -> executor.execute(cmd));

        assertThat(e.getMessage()).isEqualTo("ERROR: An unexpected error occurred");
        assertThat(e.getCause()).isEqualTo(whateverError);
    }
}