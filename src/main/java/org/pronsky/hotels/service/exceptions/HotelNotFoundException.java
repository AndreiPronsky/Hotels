package org.pronsky.hotels.service.exceptions;

public class HotelNotFoundException extends RuntimeException {

    public static final String HOTEL_NOT_FOUND_MESSAGE = "Hotel with id: %s not found";

    public HotelNotFoundException(String id) {
        super(String.format(HOTEL_NOT_FOUND_MESSAGE, id));
    }

}
