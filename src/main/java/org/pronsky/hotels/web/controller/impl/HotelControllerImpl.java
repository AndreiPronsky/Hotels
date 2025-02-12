package org.pronsky.hotels.web.controller.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.pronsky.hotels.service.HotelService;
import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.request.HotelSearchDto;
import org.pronsky.hotels.service.dto.response.ErrorResponseDto;
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
    @Operation(summary = "Get concise information about all hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReducedHotelDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))})
    })
    @GetMapping("/hotels")
    public ResponseEntity<List<ReducedHotelDto>> getAllReduced() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    @Override
    @Operation(summary = "Get full hotel information by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = FullHotelDto.class))}),
            @ApiResponse(responseCode = "404", description = "Not found", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))})
    })
    @GetMapping("/hotels/{id}")
    public ResponseEntity<FullHotelDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelService.findById(id));
    }

    @Override
    @Operation(summary = "Get concise information about hotels matching search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReducedHotelDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))})
    })
    @GetMapping("/search")
    public ResponseEntity<List<ReducedHotelDto>> search(HotelSearchDto searchParams) {
        return ResponseEntity.ok(hotelService.findAllFiltered(searchParams));
    }

    @Override
    @Operation(summary = "Add a new hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ReducedHotelDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))})
    })
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
    @Operation(summary = "Add amenities to an existing hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))})
    })
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenities(@PathVariable("id") long id, List<String> amenities) {
        hotelService.setHotelAmenities(id, amenities);
        return ResponseEntity.noContent().build();
    }

    @Override
    @Operation(summary = "Get the number of hotels grouped by each value of the specified parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponseDto.class))})
    })
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Integer>> getHistogramByParam(@PathVariable("param") String param) {
        return ResponseEntity.ok(hotelService.getHistogram(param));
    }
}
