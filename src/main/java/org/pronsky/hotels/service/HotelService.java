package org.pronsky.hotels.service;

import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.request.HotelSearchDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;

import java.util.List;
import java.util.Map;

public interface HotelService {

    List<ReducedHotelDto> findAll();

    FullHotelDto findById(long id);

    List<ReducedHotelDto> findAllFiltered(HotelSearchDto searchParams);

    ReducedHotelDto create(HotelForCreatingDto hotelDto);

    void setHotelAmenities(long id, List<String> amenitiesList);

    Map<String, Integer> getHistogram(String parameter);
}
