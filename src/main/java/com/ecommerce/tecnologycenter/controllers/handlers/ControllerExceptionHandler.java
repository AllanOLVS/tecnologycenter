package com.ecommerce.tecnologycenter.controllers.handlers;

import com.ecommerce.tecnologycenter.dto.CustomError;
import com.ecommerce.tecnologycenter.services.exceptions.DatabaseException;
import com.ecommerce.tecnologycenter.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    // Esse metodo vai ser responsavel por tratar a excessão do tipo que é passada entre os parenteses
    @ExceptionHandler(ResourceNotFoundException.class)
    // Argumentos:
    // Http... = Metodo responsavel por obter a URL que gerou a excessão.
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){

        // Pra que apareça o codigo 404, referente a requisição não encontrada
        HttpStatus status = HttpStatus.NOT_FOUND;

        // Instanciando o CustomError, com o construtor da propria classe dele
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        // Retorna o responseEntity
        return ResponseEntity.status(status).body(error);
    }

    // Esse metodo vai ser responsavel por tratar a excessão do tipo que é passada entre os parenteses
    // Neste caso é referente ao banco de dados, pra excessões que são geradas ao tentar deletar um item que esta relacionado a outro
    // "Tentar deletar um produto que esta em um pedido viola a integridade"
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

}
