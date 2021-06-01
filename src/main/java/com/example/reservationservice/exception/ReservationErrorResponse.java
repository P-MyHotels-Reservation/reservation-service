package com.example.reservationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@ToString
public enum ReservationErrorResponse implements Serializable {

  RESERVATION_IS_NOT_FOUND(HttpStatus.NOT_FOUND, "3000", "Reservation is not found."),
  ROOM_TYPE_OUT_OF_ROOM(HttpStatus.NOT_FOUND, "3001", "Room type is out of room.");

  private final HttpStatus httpStatus;
  private final String errorCode;
  private final String message;
}
