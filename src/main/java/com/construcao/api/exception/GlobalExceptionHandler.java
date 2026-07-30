package com.construcao.api.exception;

import com.construcao.api.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 1. Trata erros de validação dos DTOs (@Valid com @NotBlank, @NotNull, etc.)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
    // Mapeia cada erro de campo para a classe interna FieldErrorDTO
    List<ErrorResponseDTO.FieldErrorDTO> fieldErrors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> new ErrorResponseDTO.FieldErrorDTO(error.getField(), error.getDefaultMessage()))
        .collect(Collectors.toList());

    ErrorResponseDTO response = new ErrorResponseDTO(
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        "Um ou mais campos contêm valores inválidos.",
        fieldErrors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // 2. Trata exceções de regra de negócio ou argumentos inválidos
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
    ErrorResponseDTO response = new ErrorResponseDTO(
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // 3. Trata qualquer outra exceção não esperada (evita vazar stack trace no
  // log/response)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
    ErrorResponseDTO response = new ErrorResponseDTO(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        "Ocorreu um erro interno no servidor.");

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
