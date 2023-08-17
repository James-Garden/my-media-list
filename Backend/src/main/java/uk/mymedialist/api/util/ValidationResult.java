package uk.mymedialist.api.util;

import java.util.Optional;

public record ValidationResult(String field, String error) {

  public static final String FIELD_REQUIRED = "required";
  public static final String FIELD_INVALID = "invalid";

  public static Optional<ValidationResult> optional(String field, String error) {
    return Optional.of(new ValidationResult(field, error));
  }
}
