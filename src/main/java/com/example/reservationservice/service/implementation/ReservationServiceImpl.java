package com.example.reservationservice.service.implementation;

import com.example.reservationservice.constant.ReservationStatusConstant;
import com.example.reservationservice.exception.ReservationErrorResponse;
import com.example.reservationservice.exception.ReservationServiceException;
import com.example.reservationservice.model.entity.ReservationEntity;
import com.example.reservationservice.model.request.ReservationCreatedRequest;
import com.example.reservationservice.model.response.ReservationResponse;
import com.example.reservationservice.model.response.RoomResponse;
import com.example.reservationservice.repository.ReservationRepository;
import com.example.reservationservice.service.HotelService;
import com.example.reservationservice.service.ReservationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final HotelService hotelService;

  @Autowired
  private RestTemplate restTemplate;

  public ReservationServiceImpl(ReservationRepository reservationRepository, HotelService hotelService) {
    this.reservationRepository = reservationRepository;
    this.hotelService = hotelService;
  }

  @Override
  public ReservationResponse get(String reservationUuid) {
    return reservationRepository.findByUuid(reservationUuid).map(this::convertEntityToResponse)
        .orElseThrow(() -> new ReservationServiceException(ReservationErrorResponse.RESERVATION_IS_NOT_FOUND));
  }

  @CircuitBreaker(name = "roomBreak", fallbackMethod = "roomFallBack")
  @Override
  public ReservationResponse create(ReservationCreatedRequest reservationCreatedRequest) {

    var roomList = hotelService.getRooms(reservationCreatedRequest.getHotelId());
    log.info("roomList {}", roomList);

    var availableRoomsByRoomType =
        roomList.stream().filter(t -> reservationCreatedRequest.getRoomType().equals(t.getRoomType()))
            .map(RoomResponse::getId)
            .collect(Collectors.toList());
    log.info("roomListByRoomType {}", availableRoomsByRoomType);

    var reservedRooms = availableRoomsByRoomType.stream()
        .map(t -> reservationRepository.findAllByRoomId(t).orElse(null))
        .filter(Objects::nonNull)
        .map(t -> reservationRepository
            .findStartDate(reservationCreatedRequest.getStartDate(),
                reservationCreatedRequest.getEndDate(), t.getRoomId()))
        .collect(Collectors.toList());

    log.info("reservedRoom {}", reservedRooms);
    availableRoomsByRoomType.removeAll(reservedRooms);
    if (availableRoomsByRoomType.isEmpty()) {
      throw new ReservationServiceException(ReservationErrorResponse.ROOM_TYPE_OUT_OF_ROOM);
    } else {
      var assignedRoom = availableRoomsByRoomType.get(0);
      var reservationEntity = buildReservationEntity(reservationCreatedRequest, assignedRoom);
      return convertEntityToResponse(reservationRepository.save(reservationEntity));
    }
  }

  public ReservationResponse roomFallBack(ReservationCreatedRequest reservationCreatedRequest, Throwable t) {
    System.out.println();
    System.out.println("Hotel Id: :" +  reservationCreatedRequest.getHotelId());
    System.out.println("Room Type: " + reservationCreatedRequest.getRoomType());
    return null;
  }

  @Override
  public Boolean delete(String reservationUuid) {
    return reservationRepository.deleteByUuid(reservationUuid).map(t -> Boolean.TRUE)
        .orElseThrow(() -> new ReservationServiceException(ReservationErrorResponse.RESERVATION_IS_NOT_FOUND));
  }

  @Override
  public List<ReservationResponse> getReservationList(String guestUuid) {
    return reservationRepository.findAllByGuestUuid(guestUuid).stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  private ReservationEntity buildReservationEntity(ReservationCreatedRequest reservationCreatedRequest, BigInteger assignedRoom) {
    return ReservationEntity.builder()
        .uuid(UUID.randomUUID().toString())
        .email(reservationCreatedRequest.getEmail())
        .guestUuid(reservationCreatedRequest.getGuestUuid())
        .hotelId(reservationCreatedRequest.getHotelId())
        .phone(reservationCreatedRequest.getPhone())
        .roomId(assignedRoom)
        .roomType(reservationCreatedRequest.getRoomType())
        .status(ReservationStatusConstant.CREATED.getValue())
        .startDate(reservationCreatedRequest.getStartDate())
        .endDate(reservationCreatedRequest.getEndDate())
        .createdDate(Instant.now())
        .build();
  }

  private ReservationResponse convertEntityToResponse(ReservationEntity save) {
    return ReservationResponse.builder()
        .uuid(save.getUuid())
        .hotelId(save.getHotelId())
        .guestUuid(save.getGuestUuid())
        .email(save.getEmail())
        .phone(save.getPhone())
        .hotelId(save.getHotelId())
        .roomId(save.getRoomId())
        .roomType(save.getRoomType())
        .status(save.getStatus())
        .createdDate(save.getCreatedDate())
        .startDate(save.getStartDate())
        .endDate(save.getEndDate())
        .build();
  }
}
