package org.pronsky.hotels.service;

import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;

import java.util.List;
import java.util.Map;

public interface HotelService {

    List<ReducedHotelDto> findAll();

    FullHotelDto findById(long id);

    List<ReducedHotelDto> findAllFiltered(String name, String brand, String city, String country, Map<String, Boolean> amenities);

    ReducedHotelDto create(FullHotelDto hotel);

    void setHotelAmenities(long id, Map<String, Boolean> amenities);
}
