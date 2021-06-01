package com.example.reservationservice.service;

import com.example.reservationservice.exception.ReservationServiceException;
import com.example.reservationservice.model.entity.ReservationEntity;
import com.example.reservationservice.model.request.ReservationCreatedRequest;
import com.example.reservationservice.model.response.ReservationResponse;
import com.example.reservationservice.model.response.RoomResponse;
import com.example.reservationservice.repository.ReservationRepository;
import com.example.reservationservice.service.implementation.ReservationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ReservationServiceTest {
  private static final String EMAIL = "test";
  private static final String ROOM_ID = "1";
  private static final String HOTEL_ID = "1";

  private static final String TIME = "2021-05-20T10:16:41.928407100Z";
  private static final String ONE = "1";
  private ReservationEntity reservationEntity = ReservationEntity.builder().build();
  private ReservationResponse reservationResponse = ReservationResponse.builder().build();
  private RoomResponse roomResponse = RoomResponse.builder().build();
  @Mock
  ReservationRepository reservationRepository;
  @Mock
  HotelService hotelService;
  @Mock
  ReservationService reservationService;

  @BeforeEach
  public void init() {

    reservationService = new ReservationServiceImpl(reservationRepository, hotelService);
    reservationEntity.setEmail(EMAIL);
    reservationEntity.setHotelId(new BigInteger(HOTEL_ID));
    reservationEntity.setRoomId(new BigInteger(ROOM_ID));
    reservationEntity.setRoomType("KING_SUPERIOR_ROOM");

    reservationResponse.setEmail(EMAIL);
    reservationResponse.setHotelId(new BigInteger(HOTEL_ID));
    reservationResponse.setRoomId(new BigInteger(ROOM_ID));
    reservationResponse.setRoomType("KING_SUPERIOR_ROOM");

    roomResponse.setId(new BigInteger("1"));
    roomResponse.setHotelId(new BigInteger("1"));
    roomResponse.setRoomType("KING_SUPERIOR_ROOM");
    roomResponse.setId(new BigInteger("1"));
  }

  @Test
  void createHotel_shouldWork() {
    List<RoomResponse> roomList =  List.of(roomResponse);
    Mockito.when(hotelService.getRooms(new BigInteger("1"))).thenReturn(roomList);
    Mockito.when(reservationRepository.findAllByRoomId(new BigInteger("1"))).thenReturn(Optional.ofNullable(reservationEntity));
    Mockito.when(reservationRepository.findStartDate(Instant.parse("2018-11-30T18:35:24.00Z"), Instant.parse("2018-11-30T18:35:24.00Z"), new BigInteger("1")))
        .thenReturn(new BigInteger("5"));
    Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(reservationEntity);
    ReservationResponse result  = reservationService.create(ReservationCreatedRequest.builder()
        .hotelId(new BigInteger("1"))
        .roomType("KING_SUPERIOR_ROOM")
        .startDate(Instant.parse("2018-11-30T18:35:24.00Z"))
        .endDate(Instant.parse("2018-11-30T18:35:24.00Z")).build());
    Assertions.assertEquals(EMAIL, result.getEmail());
  }

  @Test
  void createHotelRoomTypeException_shouldWork() {
    List<RoomResponse> roomList =  List.of(roomResponse, roomResponse);
    Mockito.when(hotelService.getRooms(new BigInteger(ONE))).thenReturn(roomList);


    Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(reservationEntity);
    Assertions.assertThrows(ReservationServiceException.class, () -> reservationService.create(ReservationCreatedRequest.builder().roomType("KING_SUPERIOR_ROOM").build()));
  }

  @Test
  void getHotel_shouldWork() {
    Mockito.when(reservationRepository.findByUuid(ONE)).thenReturn(Optional.ofNullable(reservationEntity));
    ReservationResponse result  = reservationService.get(ONE);
    Assertions.assertEquals(EMAIL, result.getEmail());
  }

  @Test
  void getHotelNotFoundException_shouldWork() {
    Mockito.when(reservationRepository.findByUuid(ONE)).thenReturn(Optional.empty());
    Assertions.assertThrows(ReservationServiceException.class, () -> reservationService.get(ONE));
  }

  @Test
  void deleteHotel_shouldWork() {
    Mockito.when(reservationRepository.deleteByUuid("1")).thenReturn(Optional.ofNullable(reservationEntity));
    Boolean result  = reservationService.delete(ONE);
    Assertions.assertEquals(Boolean.TRUE, result);
  }

  @Test
  void deleteHotelNotFoundException_shouldWork() {
    Mockito.when(reservationRepository.findByUuid(ONE)).thenReturn(Optional.empty());
    Assertions.assertThrows(ReservationServiceException.class, () -> reservationService.delete(ONE));
  }

  @Test
  void getHotelList_shouldWork() {
    List<ReservationEntity> hotelResponseList =  List.of(reservationEntity);
    Mockito.when(reservationRepository.findAllByGuestUuid("1")).thenReturn(hotelResponseList);
    List<ReservationResponse> result  = reservationService.getReservationList("1");
    Assertions.assertEquals(List.of(reservationResponse), result);
  }

  private ReservationResponse toRoomResponse(ReservationEntity reservation) {
    return ReservationResponse.builder()
        .uuid(reservation.getUuid())
        .hotelId(reservation.getId())
        .status(reservation.getStatus())
        .build();
  }


}
