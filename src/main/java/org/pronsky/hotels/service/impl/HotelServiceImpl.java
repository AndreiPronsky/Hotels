package org.pronsky.hotels.service.impl;

import lombok.RequiredArgsConstructor;
import org.pronsky.hotels.data.entities.Amenities;
import org.pronsky.hotels.data.entities.Hotel;
import org.pronsky.hotels.data.repository.HotelRepository;
import org.pronsky.hotels.service.HotelService;
import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.request.HotelSearchDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;
import org.pronsky.hotels.service.exceptions.HotelNotFoundException;
import org.pronsky.hotels.service.exceptions.IncorrectAmenityException;
import org.pronsky.hotels.service.exceptions.IncorrectHistogramParamException;
import org.pronsky.hotels.service.mapper.HotelMapper;
import org.pronsky.hotels.service.specification.HotelSearchSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    public static final String FREE_PARKING = "freeParking";
    public static final String FREE_WI_FI = "freeWiFi";
    public static final String NON_SMOKING_ROOMS = "nonSmokingRooms";
    public static final String CONCIERGE = "concierge";
    public static final String ON_SITE_RESTAURANT = "onSiteRestaurant";
    public static final String FITNESS_CENTER = "fitnessCenter";
    public static final String PET_FRIENDLY_ROOMS = "petFriendlyRooms";
    public static final String ROOM_SERVICE = "roomService";
    public static final String BUSINESS_CENTER = "businessCenter";
    public static final String MEETING_ROOMS = "meetingRooms";

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<ReducedHotelDto> findAll() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::fromEntityToReducedHotelDto)
                .toList();
    }

    @Override
    public FullHotelDto findById(long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(String.valueOf(id)));
        FullHotelDto hotelDto = hotelMapper.fromEntityToFullHotelDto(hotel);
        hotelDto.setAmenities(hotelMapper.amenitiesToAmenitiesDto(hotel.getAmenities()));
        return hotelDto;
    }

    @Override
    public List<ReducedHotelDto> findAllFiltered(HotelSearchDto searchParams) {
        Specification<Hotel> specification = Specification
                .where(HotelSearchSpecification.hasCity(searchParams.getCity()))
                .and(HotelSearchSpecification.hasName(searchParams.getName()))
                .and(HotelSearchSpecification.hasBrand(searchParams.getBrand()))
                .and(HotelSearchSpecification.hasCounty(searchParams.getCountry()))
                .and(HotelSearchSpecification.hasAmenities(searchParams.getAmenities()));

        return hotelRepository.findAll(specification).stream()
                .map(hotelMapper::fromEntityToReducedHotelDto)
                .toList();
    }

    @Override
    public ReducedHotelDto create(HotelForCreatingDto hotelDto) {
        return hotelMapper.fromEntityToReducedHotelDto(
                hotelRepository.save(hotelMapper.fromHotelForCreatingDtoToHotel(hotelDto)));
    }

    @Override
    public void setHotelAmenities(long id, List<String> amenitiesList) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(String.valueOf(id)));
        hotel.setAmenities(handleAmenities(amenitiesList));
        hotelRepository.save(hotel);
    }

    @Override
    public Map<String, Integer> getHistogram(String parameter) {
        Map<String, Integer> histogram;
        List<Hotel> hotels = hotelRepository.findAll();
        histogram = switch (parameter.toLowerCase()) {
            case "brand" -> groupByBrand(hotels);
            case "city" -> groupByCity(hotels);
            case "country" -> groupByCountry(hotels);
            case "amenities" -> groupByAmenities(hotels);
            default -> throw new IncorrectHistogramParamException(parameter);
        };

        return histogram;
    }

    private Map<String, Integer> groupByBrand(List<Hotel> hotels) {
        Map<String, Integer> histogram = new HashMap<>();
        for (Hotel hotel : hotels) {
            String brand = hotel.getBrand();
            histogram.put(brand, histogram.getOrDefault(brand, 0) + 1);
        }
        return histogram;
    }

    private Map<String, Integer> groupByCity(List<Hotel> hotels) {
        Map<String, Integer> histogram = new HashMap<>();
        for (Hotel hotel : hotels) {
            String city = hotel.getAddress().getCity();
            histogram.put(city, histogram.getOrDefault(city, 0) + 1);
        }
        return histogram;
    }

    private Map<String, Integer> groupByCountry(List<Hotel> hotels) {
        Map<String, Integer> histogram = new HashMap<>();
        for (Hotel hotel : hotels) {
            String country = hotel.getAddress().getCountry();
            histogram.put(country, histogram.getOrDefault(country, 0) + 1);
        }
        return histogram;
    }

    private Map<String, Integer> groupByAmenities(List<Hotel> hotels) {
        List<String> allAmenities = List.of(
                FREE_PARKING, FREE_WI_FI, NON_SMOKING_ROOMS, CONCIERGE,
                ON_SITE_RESTAURANT, FITNESS_CENTER, PET_FRIENDLY_ROOMS,
                ROOM_SERVICE, BUSINESS_CENTER, MEETING_ROOMS
        );

        Map<String, Integer> histogram = allAmenities.stream()
                .collect(Collectors.toMap(amenity -> amenity, amenity -> 0));

        hotels.forEach(hotel -> {
            Amenities amenities = hotel.getAmenities();
            allAmenities.forEach(amenity -> {
                if (isAmenityEnabled(amenities, amenity)) {
                    histogram.put(amenity, histogram.get(amenity) + 1);
                }
            });
        });

        return histogram;
    }

    private boolean isAmenityEnabled(Amenities amenities, String amenity) {
        return switch (amenity) {
            case FREE_PARKING -> amenities.isFreeParking();
            case FREE_WI_FI -> amenities.isFreeWiFi();
            case NON_SMOKING_ROOMS -> amenities.isNonSmokingRooms();
            case CONCIERGE -> amenities.isConcierge();
            case ON_SITE_RESTAURANT -> amenities.isOnSiteRestaurant();
            case FITNESS_CENTER -> amenities.isFitnessCenter();
            case PET_FRIENDLY_ROOMS -> amenities.isPetFriendlyRooms();
            case ROOM_SERVICE -> amenities.isRoomService();
            case BUSINESS_CENTER -> amenities.isBusinessCenter();
            case MEETING_ROOMS -> amenities.isMeetingRooms();
            default -> false;
        };
    }

    private Amenities handleAmenities(List<String> amenitiesList) {
        Amenities amenities = new Amenities();
        for (String amenity : amenitiesList) {
            switch (amenity) {
                case FREE_PARKING -> amenities.setFreeParking(true);
                case FREE_WI_FI -> amenities.setFreeWiFi(true);
                case NON_SMOKING_ROOMS -> amenities.setNonSmokingRooms(true);
                case CONCIERGE -> amenities.setConcierge(true);
                case ON_SITE_RESTAURANT -> amenities.setOnSiteRestaurant(true);
                case FITNESS_CENTER -> amenities.setFitnessCenter(true);
                case PET_FRIENDLY_ROOMS -> amenities.setPetFriendlyRooms(true);
                case ROOM_SERVICE -> amenities.setRoomService(true);
                case BUSINESS_CENTER -> amenities.setBusinessCenter(true);
                case MEETING_ROOMS -> amenities.setMeetingRooms(true);
                default -> throw new IncorrectAmenityException(amenity);
            }
        }
        return amenities;
    }
}
