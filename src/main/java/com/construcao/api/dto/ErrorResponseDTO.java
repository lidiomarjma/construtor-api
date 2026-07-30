package com.construcao.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDTO {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private List<FieldErrorDTO> errors;

  public ErrorResponseDTO(int status, String error, String message) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.error = error;
    this.message = message;
  }

  public ErrorResponseDTO(int status, String error, String message, List<FieldErrorDTO> errors) {
    this(status, error, message);
    this.errors = errors;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public List<FieldErrorDTO> getErrors() {
    return errors;
  }

  public static class FieldErrorDTO {
    private String field;
    private String message;

    public FieldErrorDTO(String field, String message) {
      this.field = field;
      this.message = message;
    }

    public String getField() {
      return field;
    }

    public String getMessage() {
      return message;
    }
  }
}
