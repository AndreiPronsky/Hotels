package org.pronsky.hotels.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.pronsky.hotels.service.dto.AddressDto;
import org.pronsky.hotels.service.dto.ArrivalTimeDto;
import org.pronsky.hotels.service.dto.ContactsDto;

@Data
@Builder
@AllArgsConstructor
public class HotelForCreatingDto {

    @NotBlank
    private String name;
    private String description;
    private String brand;
    @NotNull
    private AddressDto address;
    @NotNull
    private ContactsDto contacts;
    private ArrivalTimeDto arrivalTime;
}
