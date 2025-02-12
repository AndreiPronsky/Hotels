package org.pronsky.hotels.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class HotelSearchDto {

    private String name;
    private String brand;
    private String city;
    private String country;
    private List<String> amenities;
}
