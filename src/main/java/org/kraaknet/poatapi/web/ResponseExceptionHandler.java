package org.kraaknet.poatapi.web;

import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.exceptions.ApiException;
import org.kraaknet.poatapi.exceptions.AuthorizationException;
import org.kraaknet.poatapi.exceptions.BadRequestException;
import org.kraaknet.poatapi.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    private ErrorMessage handleBadRequestException(BadRequestException exception) {
        log.debug("BadRequestException(%s)", exception);
        return new ErrorMessage(exception.getMessage());
    }


    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleApiServiceException(ApiException exception) {
        log.info("handleApiServiceException(%s)", exception);
        return ErrorMessage.builder().message(exception.getMessage()).build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(NotFoundException exception) {
        log.debug("handleNotFoundException(%s)", exception);
        return ErrorMessage.builder().message(exception.getMessage()).build();
    }


    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleAuthorizationException(AuthorizationException e) {
        log.debug("handleAuthorizationException(%s)", e);
        return ErrorMessage.builder().message(e.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(Exception e) {
        log.error("Unexpected exception: ", e);
        return ErrorMessage.builder().message("Unknown internal error occurred").build();
    }

}
