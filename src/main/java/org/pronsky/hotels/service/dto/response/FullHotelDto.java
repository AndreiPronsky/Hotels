package org.pronsky.hotels.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.pronsky.hotels.service.dto.AddressDto;
import org.pronsky.hotels.service.dto.AmenitiesDto;
import org.pronsky.hotels.service.dto.ArrivalTimeDto;
import org.pronsky.hotels.service.dto.ContactsDto;

@Data
@Builder
@AllArgsConstructor
public class FullHotelDto {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactsDto contacts;
    private ArrivalTimeDto arrivalTime;
    private AmenitiesDto amenities;
}
