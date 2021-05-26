package com.example.reservationservice.controller;

import com.example.reservationservice.controller.implementation.ReservationController;
import com.example.reservationservice.model.request.ReservationCreatedRequest;
import com.example.reservationservice.model.response.ReservationResponse;
import com.example.reservationservice.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class HotelControllerTest {


  public IReservationController iReservationController;
  public ReservationService reservationService;

  @BeforeEach
  public void init() {
    reservationService = Mockito.mock(ReservationService.class);
    iReservationController = new ReservationController(reservationService);
  }

  @Test
  void createHotelController_shouldWork() {
    Mockito.when(reservationService.create(Mockito.any(ReservationCreatedRequest.class)))
        .thenReturn(ReservationResponse.builder().build());

    ResponseEntity<ReservationResponse> responses = iReservationController.createReservation(Mockito.any(ReservationCreatedRequest.class));

    Assertions.assertEquals(HttpStatus.OK, responses.getStatusCode());
  }

  @Test
  void getHotelController_shouldWork() {
    Mockito.when(reservationService.get("1"))
        .thenReturn(ReservationResponse.builder().build());

    ResponseEntity<ReservationResponse> responses = iReservationController.getReservation("1");

    Assertions.assertEquals(HttpStatus.OK, responses.getStatusCode());
  }

  @Test
  void deleteHotelController_shouldWork() {
    Mockito.when(reservationService.delete("1"))
        .thenReturn(Boolean.TRUE);

    ResponseEntity<Boolean> responses = iReservationController.deleteReservation("1");

    Assertions.assertEquals(HttpStatus.OK, responses.getStatusCode());
  }

  @Test
  void getHotelListController_shouldWork() {
    List<ReservationResponse> reservationResponseList =  new ArrayList<>();
    Mockito.when(reservationService.getReservationList("1"))
        .thenReturn(reservationResponseList);

    ResponseEntity<List<ReservationResponse>> responses = iReservationController.getReservationList("1");

    Assertions.assertEquals(HttpStatus.OK, responses.getStatusCode());
  }
}
