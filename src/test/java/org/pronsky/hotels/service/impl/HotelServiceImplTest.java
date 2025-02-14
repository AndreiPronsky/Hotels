package org.pronsky.hotels.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pronsky.hotels.data.entities.Address;
import org.pronsky.hotels.data.entities.Amenities;
import org.pronsky.hotels.data.entities.ArrivalTime;
import org.pronsky.hotels.data.entities.Contacts;
import org.pronsky.hotels.data.entities.Hotel;
import org.pronsky.hotels.data.repository.HotelRepository;
import org.pronsky.hotels.service.dto.AddressDto;
import org.pronsky.hotels.service.dto.ArrivalTimeDto;
import org.pronsky.hotels.service.dto.ContactsDto;
import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.request.HotelSearchDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;
import org.pronsky.hotels.service.exceptions.HotelNotFoundException;
import org.pronsky.hotels.service.exceptions.IncorrectHistogramParamException;
import org.pronsky.hotels.service.mapper.HotelMapper;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Hotel hotel;
    private Hotel hotelToCreate;
    private HotelForCreatingDto hotelToCreateDto;
    private Hotel createdHotel;
    private Amenities amenities;
    private Address address;
    private AddressDto addressDto;
    private Contacts contacts;
    private ContactsDto contactsDto;
    private ArrivalTime arrivalTime;
    private ArrivalTimeDto arrivalTimeDto;
    private ReducedHotelDto reducedHotelDto;
    private FullHotelDto fullHotelDto;
    private List<String> amenitiesList;
    private HotelSearchDto hotelSearchDto;

    @BeforeEach
    void setUp() {
        amenities = Amenities.builder()
                .id(1L)
                .freeParking(true)
                .freeWiFi(true)
                .nonSmokingRooms(true)
                .concierge(false)
                .onSiteRestaurant(false)
                .fitnessCenter(true)
                .petFriendlyRooms(true)
                .roomService(false)
                .businessCenter(false)
                .meetingRooms(false)
                .build();

        address = Address.builder()
                .id(1L)
                .houseNumber("9")
                .street("Pobediteley")
                .city("Minsk")
                .country("Belarus")
                .postCode("220004")
                .build();

        contacts = Contacts.builder()
                .id(1L)
                .phone("+375 29 1111111")
                .email("someTest_email@mail.com")
                .build();

        arrivalTime = ArrivalTime.builder()
                .id(1L)
                .checkIn(LocalTime.of(13, 0))
                .checkOut(LocalTime.of(12, 0))
                .build();

        hotel = Hotel.builder()
                .id(1L)
                .name("Double Tree")
                .description("Test description")
                .brand("Hilton")
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .amenities(amenities)
                .build();

        addressDto = AddressDto.builder()
                .houseNumber("9")
                .street("Pobediteley")
                .city("Minsk")
                .country("Belarus")
                .postCode("220004")
                .build();

        contactsDto = ContactsDto.builder()
                .phone("+375 29 1111111")
                .email("someTest_email@mail.com")
                .build();

        arrivalTimeDto = ArrivalTimeDto.builder()
                .checkIn(LocalTime.of(13, 0))
                .checkOut(LocalTime.of(12, 0))
                .build();

        amenitiesList = new ArrayList<>();
        amenitiesList.add("Free parking");
        amenitiesList.add("Free WiFi");
        amenitiesList.add("Non-smoking rooms");
        amenitiesList.add("Fitness center");
        amenitiesList.add("Pet-friendly rooms");

        hotelToCreateDto = HotelForCreatingDto.builder()
                .name("Test name")
                .description("Test descr")
                .brand("Test brand")
                .address(addressDto)
                .contacts(contactsDto)
                .arrivalTime(arrivalTimeDto)
                .build();
        hotelToCreate = Hotel.builder()
                .name("Test name")
                .description("Test descr")
                .brand("Test brand")
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .build();
        createdHotel = Hotel.builder()
                .id(2L)
                .name("Test name")
                .description("Test descr")
                .brand("Test brand")
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .build();

        reducedHotelDto = ReducedHotelDto.builder()
                .id(1L)
                .name("Test name")
                .description("Test descr")
                .address(address.toString())
                .phone("+375 29 1111111")
                .build();

        fullHotelDto = FullHotelDto.builder()
                .id(1L)
                .name("Double Tree")
                .description("Test description")
                .brand("Hilton")
                .address(addressDto)
                .contacts(contactsDto)
                .arrivalTime(arrivalTimeDto)
                .amenities(amenitiesList)
                .build();

        hotelSearchDto = HotelSearchDto.builder()
                .name("Test")
                .brand("Hilton")
                .city("Minsk")
                .country("VKL")
                .amenities(amenitiesList)
                .build();
    }

    @Test
    void findAll_ShouldReturnListOfReducedHotelDto() {
        when(hotelRepository.findAll()).thenReturn(Collections.singletonList(hotel));
        when(hotelMapper.fromEntityToReducedHotelDto(hotel)).thenReturn(reducedHotelDto);

        List<ReducedHotelDto> result = hotelService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reducedHotelDto, result.get(0));
        verify(hotelRepository, times(1)).findAll();
        verify(hotelMapper, times(1)).fromEntityToReducedHotelDto(hotel);
    }

    @Test
    void findById_WhenHotelExists_ShouldReturnFullHotelDto() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelMapper.fromEntityToFullHotelDto(hotel)).thenReturn(fullHotelDto);
        when(hotelMapper.mapAmenities(amenities)).thenReturn(amenitiesList);

        FullHotelDto result = hotelService.findById(1L);

        assertNotNull(result);
        assertEquals(fullHotelDto, result);
        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelMapper, times(1)).fromEntityToFullHotelDto(hotel);
        verify(hotelMapper, times(1)).mapAmenities(amenities);
    }

    @Test
    void findById_WhenHotelDoesNotExist_ShouldThrowHotelNotFoundException() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.findById(1L));
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void findAllFiltered_ShouldReturnFilteredListOfReducedHotelDto() {

        when(hotelRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(hotel));
        when(hotelMapper.fromEntityToReducedHotelDto(hotel)).thenReturn(reducedHotelDto);

        List<ReducedHotelDto> result = hotelService.findAllFiltered(hotelSearchDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reducedHotelDto, result.get(0));
        verify(hotelRepository, times(1)).findAll(any(Specification.class));
        verify(hotelMapper, times(1)).fromEntityToReducedHotelDto(hotel);
    }

    @Test
    void create_ShouldSaveAndReturnReducedHotelDto() {
        when(hotelMapper.fromHotelForCreatingDtoToHotel(any(HotelForCreatingDto.class))).thenReturn(hotelToCreate);
        when(hotelRepository.save(hotelToCreate)).thenReturn(createdHotel);
        when(hotelMapper.fromEntityToReducedHotelDto(any(Hotel.class))).thenReturn(reducedHotelDto);

        ReducedHotelDto result = hotelService.create(hotelToCreateDto);

        assertNotNull(result);
        assertEquals(reducedHotelDto, result);
        verify(hotelMapper, times(1)).fromHotelForCreatingDtoToHotel(any());
        verify(hotelRepository, times(1)).save(hotelToCreate);
        verify(hotelMapper, times(1)).fromEntityToReducedHotelDto(any());
    }

    @Test
    void setHotelAmenities_WhenHotelExists_ShouldUpdateAmenities() {

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        hotelService.setHotelAmenities(1L, amenitiesList);

        assertTrue(hotel.getAmenities().isFreeParking());
        assertTrue(hotel.getAmenities().isFreeWiFi());
        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void setHotelAmenities_WhenHotelDoesNotExist_ShouldThrowHotelNotFoundException() {

        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.setHotelAmenities(1L, amenitiesList));
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    void getHistogram_WhenParameterIsBrand_ShouldReturnBrandHistogram() {
        List<Hotel> hotels = List.of(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);

        Map<String, Integer> result = hotelService.getHistogram("brand");

        assertNotNull(result);
        assertEquals(1, result.get(hotel.getBrand()));
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    void getHistogram_WhenParameterIsIncorrect_ShouldThrowIncorrectHistogramParamException() {
        assertThrows(IncorrectHistogramParamException.class, () -> hotelService.getHistogram("invalidParam"));
    }
}
