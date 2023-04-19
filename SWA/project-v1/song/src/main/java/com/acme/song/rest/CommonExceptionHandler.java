package com.acme.song.rest;

import com.acme.song.service.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    private static final String NOTFOUNDMESSAGE= "NotFound: {}";

    @ExceptionHandler(NotFoundException.class)
    void notFound(final NotFoundException e) {
        log.debug(NOTFOUNDMESSAGE, e.getMessage());
    }
}
