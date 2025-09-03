package com.movieawards.api.application.usecase.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UseCase<Input, Output> {

    protected Logger getLogger() {
        return LoggerFactory.getLogger(getClazz());
    }

    public abstract Output execute(Input value);

    public abstract Class<?> getClazz();
}
