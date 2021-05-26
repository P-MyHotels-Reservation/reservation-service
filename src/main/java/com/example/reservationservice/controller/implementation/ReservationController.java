package com.example.reservationservice.controller.implementation;

import com.example.reservationservice.controller.IReservationController;
import com.example.reservationservice.model.request.ReservationCreatedRequest;
import com.example.reservationservice.model.response.ReservationResponse;
import com.example.reservationservice.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ReservationController implements IReservationController {

  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }


  @Override
  public ResponseEntity<ReservationResponse> getReservation(String reservationId) {
    return ResponseEntity.ok(reservationService.get(reservationId));
  }

  @Override
  public ResponseEntity<ReservationResponse> createReservation(ReservationCreatedRequest reservationCreatedRequest) {
    return ResponseEntity.ok(reservationService.create(reservationCreatedRequest));
  }

  @Override
  public ResponseEntity<Boolean> deleteReservation(String reservationUuid) {
    return ResponseEntity.ok(reservationService.delete(reservationUuid));
  }

  @Override
  public ResponseEntity<List<ReservationResponse>> getReservationList(String guestUuid) {
    return ResponseEntity.ok(reservationService.getReservationList(guestUuid));
  }
}
