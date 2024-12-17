package ${groupId}.${artifactIdToPackageImport}.adapter.config;

import ${groupId}.${artifactIdToPackageImport}.core.config.exceptions.NotFoundException;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.concurrent.TimeoutException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ NotFoundException.class })
  public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    log.error(problemDetail.getDetail(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler({ IllegalArgumentException.class })
  public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    log.error(problemDetail.getDetail(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler({ NullPointerException.class })
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ProblemDetail> handleNullPointerException(NullPointerException e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    log.error(problemDetail.getDetail(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler({ CallNotPermittedException.class })
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public ResponseEntity<ProblemDetail> handleCallNotPermittedException(CallNotPermittedException e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    log.error(problemDetail.getDetail(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler({ TimeoutException.class })
  @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
  public ResponseEntity<ProblemDetail> handleTimeoutException(TimeoutException e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.REQUEST_TIMEOUT, e.getMessage());
    log.error(problemDetail.getDetail(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler({ BulkheadFullException.class })
  @ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
  public ResponseEntity<ProblemDetail> handleBulkheadFullException(BulkheadFullException e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, e.getMessage());
    log.error(problemDetail.getDetail(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler({ RequestNotPermitted.class })
  @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
  public ResponseEntity<ProblemDetail> handleRequestNotPermitted(RequestNotPermitted e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.TOO_MANY_REQUESTS, e.getMessage());
    log.error(problemDetail.getDetail(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

  @ExceptionHandler({ MethodArgumentNotValidException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    e.getBindingResult().getFieldErrors()
      .forEach(fieldError -> problemDetail.setProperty(fieldError.getField(), fieldError.getDefaultMessage()));
    log.error("{} - Errors : {}", problemDetail.getDetail(), problemDetail.getProperties(), e);
    return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
  }

}