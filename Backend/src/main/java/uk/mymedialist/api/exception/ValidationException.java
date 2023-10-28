package uk.mymedialist.api.exception;

import java.util.List;
import uk.mymedialist.api.util.ValidationResult;

public class ValidationException extends RuntimeException {

  private final transient List<ValidationResult> validationResults;

  public ValidationException(List<ValidationResult> validationResults) {
    this.validationResults = validationResults;
  }

  public List<ValidationResult> getValidationResults() {
    return validationResults;
  }
}
