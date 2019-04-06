package com.jbenitoc.cabifystoreclient.infrastructure.command;

import com.jbenitoc.cabifystoreclient.domain.model.DomainError;
import com.jbenitoc.cabifystoreclient.domain.model.UnexpectedError;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class CommandExecutor {

    public String execute(Command<String> command) {
        try {
            return command.get();
        } catch (DomainError e) {
            throw e;
        } catch (ResourceAccessException e) {
            throw new UnexpectedError("ERROR: It was impossible to connect to server.", e);
        } catch (Exception e) {
            throw new UnexpectedError("ERROR: An unexpected error occurred", e);
        }
    }
}
