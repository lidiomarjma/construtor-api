package com.construcao.api.exception;

import com.construcao.api.dto.ErroRespostaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Captura erros de validação (@Valid nov DTOs)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErroRespostaDTO> tratarErrosDeValidacao(MethodArgumentNotValidException ex) {
    // Extrai apenas as mensagens de erro configuradas nas anotações (@NotBlank,
    // @Size, etc.)
    List<String> erros = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
        .collect(Collectors.toList());

    ErroRespostaDTO resposta = new ErroRespostaDTO(
        HttpStatus.BAD_REQUEST.value(),
        "Erro de Validação nos Dados Enviados",
        erros);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
  }
}
