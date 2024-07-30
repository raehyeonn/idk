package team.onepoom.idk.common.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.onepoom.idk.common.exception.ConflictException;
import team.onepoom.idk.common.exception.ForbiddenException;
import team.onepoom.idk.common.exception.NotFoundException;
import team.onepoom.idk.common.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    //접근 권한
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException e) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    //리소스를 찾을 수 없는 경우
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //중복 예외 체크
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException e) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getBindingResult());
        log.error("Validation error: ", e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
        ConstraintViolationException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getConstraintViolations());
        log.error("Constraint violation: ", e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED,
            e.getMessage());
        log.error("Method not allowed: ", e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
        MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(Exception e) {
        String message = (e instanceof HttpMessageNotReadableException)
            ? "Required request body is missing"
            : e.getMessage();
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, message);
        log.error("Bad request: ", e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandled exception occurred: ", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
            "서버 내부 오류가 발생했습니다.");
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
