package org.pronsky.hotels.service.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Data
public class ErrorResponseDto {

    private int status;
    private String error;
    private String path;

    public ErrorResponseDto(String message, HttpStatus status, WebRequest request) {

        this.status = status.value();
        this.error = message;
        this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}
