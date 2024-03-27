package cards.logic.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application, capturing exceptions thrown by any controller
 * and providing a uniform response structure. This class uses the {@link ControllerAdvice} annotation
 * to define a global advice that applies to all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles exceptions thrown when method argument annotated with {@code @Valid} fails validation.
   *
   * @param methodArgumentNotValidException The exception that was thrown due to validation failure.
   * @param request The web request during which the exception was thrown.
   * @return A {@link ResponseEntity} object containing the details of the validation errors
   *         including the timestamp, the HTTP status, and the validation error messages.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(
          MethodArgumentNotValidException methodArgumentNotValidException, WebRequest request) {

    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());

    Map<String, String> errors = new HashMap<>();
    BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
    bindingResult.getFieldErrors().forEach(fieldError ->
            errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
    bindingResult.getGlobalErrors().forEach(globalError ->
            errors.put(globalError.getObjectName(), globalError.getDefaultMessage()));

    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}

