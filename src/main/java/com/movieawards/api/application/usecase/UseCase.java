package com.movieawards.api.application.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UseCase<Input, Output> {

    protected Logger getLogger() {
        return LoggerFactory.getLogger(UseCase.class);
    }

    public abstract Output execute(Input value);
}
