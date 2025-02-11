package org.pronsky.hotels.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ArrivalTimeDto {

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
