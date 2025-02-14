package org.pronsky.hotels.web.controller.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.pronsky.hotels.service.dto.response.ErrorResponseDto;
import org.pronsky.hotels.service.exceptions.HotelNotFoundException;
import org.pronsky.hotels.service.exceptions.IncorrectAmenityException;
import org.pronsky.hotels.service.exceptions.IncorrectHistogramParamException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    private static final String CONSTRAINT_VIOLATION_MESSAGE = "DB Constraint violation";

    @ExceptionHandler({
            HotelNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IncorrectAmenityException.class,
            IncorrectHistogramParamException.class,
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                CONSTRAINT_VIOLATION_MESSAGE,
                HttpStatus.UNPROCESSABLE_ENTITY,
                request);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                INTERNAL_SERVER_ERROR_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
