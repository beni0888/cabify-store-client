package com.jbenitoc.cabifystoreclient.infrastructure.command;

@FunctionalInterface
public interface Command<T> {

    /**
     * Executes a task and return a result.
     *
     * @return a result
     */
    T get();
}
