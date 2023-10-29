package uk.mymedialist.api.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.mymedialist.api.util.ValidationResult;

@RestControllerAdvice
public class RequestExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public List<ValidationResult> validationExceptionHandler(ValidationException validationException) {
    return validationException.getValidationResults();
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String entityNotFoundExceptionHandler(EntityNotFoundException exception) {
    return exception.getMessage();
  }

}
