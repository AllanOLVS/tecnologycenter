package com.ecommerce.tecnologycenter.controllers.handlers;

import com.ecommerce.tecnologycenter.dto.CustomError;
import com.ecommerce.tecnologycenter.dto.FieldMessage;
import com.ecommerce.tecnologycenter.dto.ValidationError;
import com.ecommerce.tecnologycenter.services.exceptions.DatabaseException;
import com.ecommerce.tecnologycenter.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // Personalizando retorno das exceções com os argumentos invalidos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValid(MethodArgumentNotValidException e,HttpServletRequest request){
        //Codigo 422, pra dados invalidos
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Dados invalidos", request.getRequestURI());

        // e.getBindingResult().getFieldErrors() = Este metodo retorna uma lista do tipo fieldError
        // Na lista que o metodo retorna, vai conter todas as excessões que foram geradas, conforme as @ANOTATIONS que
        //      Estiverem no ProductDTO
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            // Adicionando o erro de validação na lista de erros que esta dentro em ValidationError
            //Tod erro de validação vai ver armazenado la e mostrado quando os erros forem capturados
            error.addErrorsToList(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }

}
