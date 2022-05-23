package com.carly.exception;

import com.carly.model.dto.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    private static final String EUROPE_ATHENS_TZ = "Europe/Athens";

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<ErrorDTO> handle(ResourceExistsException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(ZoneId.of(EUROPE_ATHENS_TZ)),
                ex.getMessage());
        log.warn(ex.getMessage(), ex);
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handle(ResourceNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(ZoneId.of(EUROPE_ATHENS_TZ)),
                ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> handle(BadCredentialsException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(ZoneId.of(EUROPE_ATHENS_TZ)),
                ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

}

