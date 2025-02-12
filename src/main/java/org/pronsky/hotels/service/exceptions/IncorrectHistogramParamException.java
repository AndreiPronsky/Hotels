package org.pronsky.hotels.service.exceptions;

public class IncorrectHistogramParamException extends RuntimeException {

    public static final String INCORRECT_PARAM_MESSAGE = "Incorrect histogram param: %s";

    public IncorrectHistogramParamException(String param) {
        super(String.format(INCORRECT_PARAM_MESSAGE, param));
    }
}
