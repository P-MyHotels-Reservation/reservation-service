package com.example.reservationservice.constant;

public enum ReservationStatusConstant {
  CREATED("CREATED"),
  READY_FOR_PAYMENT("READY_FOR_PAYMENT"),
  PAID("PAID"),
  DONE("DONE");

  private String value;

  ReservationStatusConstant(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
