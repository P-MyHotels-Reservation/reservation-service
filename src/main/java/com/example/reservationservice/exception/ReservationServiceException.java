package com.example.reservationservice.exception;

import lombok.Getter;

@Getter
public class ReservationServiceException extends RuntimeException {

  final ReservationErrorResponse reservationErrorResponse;

  public ReservationServiceException(ReservationErrorResponse reservationErrorResponse) {
    this.reservationErrorResponse = reservationErrorResponse;
  }
}
