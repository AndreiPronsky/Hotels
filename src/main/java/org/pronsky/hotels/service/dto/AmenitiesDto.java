package org.pronsky.hotels.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AmenitiesDto {
private List<String> amenities;
}
