package org.pronsky.hotels.web.controller.impl;

import lombok.RequiredArgsConstructor;
import org.pronsky.hotels.service.HotelService;
import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.request.HotelSearchDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;
import org.pronsky.hotels.web.controller.HotelController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view")
public class HotelControllerImpl implements HotelController {

    private final HotelService hotelService;

    @Override
    @GetMapping("/hotels")
    public ResponseEntity<List<ReducedHotelDto>> getAllReduced() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    @Override
    @GetMapping("/hotels/{id}")
    public ResponseEntity<FullHotelDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelService.findById(id));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<ReducedHotelDto>> search(HotelSearchDto searchParams) {
        return ResponseEntity.ok(hotelService.findAllFiltered(searchParams));
    }

    @Override
    @PostMapping("/hotels")
    public ResponseEntity<ReducedHotelDto> createHotel(@RequestBody HotelForCreatingDto hotel) {
        ReducedHotelDto hotelDto = hotelService.create(hotel);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hotelDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(hotelDto);
    }

    @Override
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenities(@PathVariable("id") long id, List<String> amenities) {
        hotelService.setHotelAmenities(id, amenities);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Integer>> getHistogramByParam(@PathVariable("param") String param) {
        return ResponseEntity.ok(hotelService.getHistogram(param));
    }
}
