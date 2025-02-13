package org.pronsky.hotels.web.controller.handler;

import org.pronsky.hotels.service.dto.response.ErrorResponseDto;
import org.pronsky.hotels.service.exceptions.HotelNotFoundException;
import org.pronsky.hotels.service.exceptions.IncorrectAmenityException;
import org.pronsky.hotels.service.exceptions.IncorrectHistogramParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";

    @ExceptionHandler({
            HotelNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(Exception e, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IncorrectAmenityException.class,
            IncorrectHistogramParamException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(Exception e, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            RuntimeException.class
    })
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                INTERNAL_SERVER_ERROR_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
