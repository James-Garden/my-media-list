package uk.mymedialist.api.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import uk.mymedialist.api.exception.ValidationException;

public class ValidatorTestUtil {

  private ValidatorTestUtil() {
  }

  public static List<ValidationResult> getValidationErrors(Runnable runnable) {
    try {
      runnable.run();
      fail("Expected runnable to throw a ValidationException but it did not");
      return null; // appeasing the compiler: this line will never be executed.
    } catch (ValidationException validationException) {
      return validationException.getValidationResults();
    }
  }

}
