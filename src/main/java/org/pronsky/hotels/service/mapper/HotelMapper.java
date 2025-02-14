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
import org.pronsky.hotels.service.dto.ArrivalTimeDto;
import org.pronsky.hotels.service.dto.ContactsDto;
import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    String FREE_PARKING = "Free parking";
    String FREE_WI_FI = "Free WiFi";
    String NON_SMOKING_ROOMS = "Non-smoking rooms";
    String CONCIERGE = "Concierge";
    String ON_SITE_RESTAURANT = "On-site restaurant";
    String FITNESS_CENTER = "Fitness center";
    String PET_FRIENDLY_ROOMS = "Pet-friendly rooms";
    String ROOM_SERVICE = "Room service";
    String BUSINESS_CENTER = "Business center";
    String MEETING_ROOMS = "Meeting rooms";

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
    @Mapping(expression = "java(mapAmenities(hotel.getAmenities()))", target = "amenities")
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

    default List<String> mapAmenities(Amenities amenities) {
        return Stream.of(
                        amenities.isFreeParking() ? FREE_PARKING : "",
                        amenities.isFreeWiFi() ? FREE_WI_FI : "",
                        amenities.isNonSmokingRooms() ? NON_SMOKING_ROOMS : "",
                        amenities.isConcierge() ? CONCIERGE : "",
                        amenities.isOnSiteRestaurant() ? ON_SITE_RESTAURANT : "",
                        amenities.isFitnessCenter() ? FITNESS_CENTER : "",
                        amenities.isPetFriendlyRooms() ? PET_FRIENDLY_ROOMS : "",
                        amenities.isRoomService() ? ROOM_SERVICE : "",
                        amenities.isBusinessCenter() ? BUSINESS_CENTER : "",
                        amenities.isMeetingRooms() ? MEETING_ROOMS : ""
                )
                .filter(amenity -> !amenity.isBlank())
                .toList();
    }
}
