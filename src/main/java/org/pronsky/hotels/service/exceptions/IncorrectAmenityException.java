package org.pronsky.hotels.service.exceptions;

public class IncorrectAmenityException extends RuntimeException {

    public static final String INCORRECT_AMENITY_MESSAGE = "Incorrect amenity: %s";

    public IncorrectAmenityException(String amenity) {
        super(String.format(INCORRECT_AMENITY_MESSAGE, amenity));
    }
}
