package personal.GesundKlinik.shared.exception.global;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import personal.GesundKlinik.shared.dto.ProblemResponse;
import personal.GesundKlinik.shared.exception.EmailInUseException;
import personal.GesundKlinik.shared.exception.NotFoundException;
import personal.GesundKlinik.shared.exception.PhoneInUseException;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

//"extends ResponseEntityExceptionHandler" permite herdar e customizar o comportamento padrão do Spring para exceções (ex: validações com @Valid).
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            EmailInUseException.class,
            PhoneInUseException.class
    })
    public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        log.warn("Conflict: {}", ex.getMessage());
        return buildResponse(ex, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler({
            NotFoundException.class
    })
    public ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        log.warn("Not Found: {}", ex.getMessage());
        return buildResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpected(Exception ex, WebRequest request) {
        log.error("Unexpected error: ", ex);
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    //  Tratamento de validações com @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        // Extrai os erros de validação dos campos
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Campo '%s': %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        // Cria o corpo da resposta padronizada
        var problem = ProblemResponse.builder()
                .status(status.value()) // usa o status recebido (tipicamente 400)
                .timestamp(OffsetDateTime.now())
                .message(message)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String formatFieldError(FieldError error) {
        return String.format("Field '%s': %s", error.getField(), error.getDefaultMessage());
    }

    private ResponseEntity<Object> buildResponse(Exception ex, HttpStatus status, WebRequest request) {
        var problem = ProblemResponse.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .message(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
}