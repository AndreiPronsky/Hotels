package org.pronsky.hotels.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.pronsky.hotels.data.entities.Address;
import org.pronsky.hotels.data.entities.Amenities;
import org.pronsky.hotels.data.entities.ArrivalTime;
import org.pronsky.hotels.data.entities.Contacts;
import org.pronsky.hotels.data.entities.Hotel;
import org.pronsky.hotels.service.dto.AddressDto;
import org.pronsky.hotels.service.dto.AmenitiesDto;
import org.pronsky.hotels.service.dto.ArrivalTimeDto;
import org.pronsky.hotels.service.dto.ContactsDto;
import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    @Mapping(source = "hotel.id", target = "id")
    @Mapping(source = "hotel.name", target = "name")
    @Mapping(source = "hotel.description", target = "description")
    @Mapping(expression = "java(hotel.getAddress().toString())", target = "address")
    @Mapping(source = "hotel.contacts.phone", target = "phone")
    ReducedHotelDto fromEntityToReducedHotelDto(Hotel hotel);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "brand", target = "brand")
    @Mapping(expression = "java(amenitiesToAmenitiesDto(hotel.getAmenities()))", target = "amenities")
    @Mapping(expression = "java(contactsToContactsDto(hotel.getContacts()))", target = "contacts")
    @Mapping(expression = "java(addressToAddressDto(hotel.getAddress()))", target = "address")
    @Mapping(expression = "java(arrivalTimeToArrivalTimeDto(hotel.getArrivalTime()))", target = "arrivalTime")
    FullHotelDto fromEntityToFullHotelDto(Hotel hotel);

    @Mapping(source = "houseNumber", target = "houseNumber")
    @Mapping(source = "street", target = "street")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "postCode", target = "postCode")
    AddressDto addressToAddressDto(Address address);

    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    ContactsDto contactsToContactsDto(Contacts contacts);

    @Mapping(source = "checkIn", target = "checkIn")
    @Mapping(source = "checkOut", target = "checkOut")
    ArrivalTimeDto arrivalTimeToArrivalTimeDto(ArrivalTime arrivalTime);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "address.houseNumber", target = "address.houseNumber")
    @Mapping(source = "address.street", target = "address.street")
    @Mapping(source = "address.city", target = "address.city")
    @Mapping(source = "address.country", target = "address.country")
    @Mapping(source = "address.postCode", target = "address.postCode")
    @Mapping(source = "contacts.phone", target = "contacts.phone")
    @Mapping(source = "contacts.email", target = "contacts.email")
    @Mapping(source = "arrivalTime.checkIn", target = "arrivalTime.checkIn")
    @Mapping(source = "arrivalTime.checkOut", target = "arrivalTime.checkOut")
    Hotel fromHotelForCreatingDtoToHotel(HotelForCreatingDto hotelForCreatingDto);

    default AmenitiesDto amenitiesToAmenitiesDto(Amenities amenities) {
        List<String> amenitiesList = Stream.of(
                        "freeParking",
                        "freeWiFi",
                        "nonSmokingRooms",
                        "concierge",
                        "onSiteRestaurant",
                        "fitnessCenter",
                        "petFriendlyRooms",
                        "roomService",
                        "businessCenter",
                        "meetingRooms"
                )
                .filter(fieldName -> {
                    switch (fieldName) {
                        case "freeParking" -> { return amenities.isFreeParking(); }
                        case "freeWiFi" -> { return amenities.isFreeWiFi(); }
                        case "nonSmokingRooms" -> { return amenities.isNonSmokingRooms(); }
                        case "concierge" -> { return amenities.isConcierge(); }
                        case "onSiteRestaurant" -> { return amenities.isOnSiteRestaurant(); }
                        case "fitnessCenter" -> { return amenities.isFitnessCenter(); }
                        case "petFriendlyRooms" -> { return amenities.isPetFriendlyRooms(); }
                        case "roomService" -> { return amenities.isRoomService(); }
                        case "businessCenter" -> { return amenities.isBusinessCenter(); }
                        case "meetingRooms" -> { return amenities.isMeetingRooms(); }
                        default -> { return false; }
                    }
                })
                .toList();
        return new AmenitiesDto(amenitiesList);
    }
}
