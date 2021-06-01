package com.example.reservationservice.exception;

import brave.Tracer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReservationExceptionConverter {

  private final ObjectMapper mapper = new ObjectMapper();
  private final Tracer tracer;

  public ReservationExceptionConverter(Tracer tracer) {
    this.tracer = tracer;
  }

  public JsonNode toJsonNode(ReservationErrorResponse errorDetail, String extraError) {
    var rootNode = this.mapper.createObjectNode();
    rootNode.put("errorCode", errorDetail.getErrorCode());
    rootNode.put("errorMessage", errorDetail.getMessage().concat(extraError));
    rootNode.put("traceId", getTraceId());
    return rootNode;
  }


  private String getTraceId() {
    if (Objects.isNull(tracer)
        || Objects.isNull(tracer.currentSpan())
        || Objects.isNull(tracer.currentSpan().context())) {
      return StringUtils.EMPTY;
    }

    return tracer.currentSpan().context().traceIdString();
  }
}
