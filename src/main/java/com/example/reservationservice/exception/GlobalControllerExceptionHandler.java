package com.example.reservationservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  private final ReservationExceptionConverter reservationExceptionConverter;

  public GlobalControllerExceptionHandler(ReservationExceptionConverter reservationExceptionConverter) {
    this.reservationExceptionConverter = reservationExceptionConverter;
  }

  @ExceptionHandler(ReservationServiceException.class)
  public ResponseEntity<Object> handleAccountNotFoundException(ReservationServiceException ex) {
    log.error("ReservationException", ex);

    return new ResponseEntity<>(
        reservationExceptionConverter.toJsonNode(ex.getReservationErrorResponse(), StringUtils.EMPTY),
        new HttpHeaders(), ex.getReservationErrorResponse().getHttpStatus());
  }
}
