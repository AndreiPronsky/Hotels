package org.pronsky.hotels.web.controller;

import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface HotelController {

    ResponseEntity<List<ReducedHotelDto>> getAllReduced();

    ResponseEntity<FullHotelDto> getById(Long id);

    ResponseEntity<List<ReducedHotelDto>> search(String name,
                                                 String brand,
                                                 String city,
                                                 String country,
                                                 List<String> amenities);

    ResponseEntity<ReducedHotelDto> createHotel(HotelForCreatingDto hotel);

    ResponseEntity<Void> addAmenities(long id, List<String> amenities);

    ResponseEntity<Map<String, Integer>> getHistogramByParam(String param);
}
