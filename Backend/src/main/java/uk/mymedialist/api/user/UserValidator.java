package uk.mymedialist.api.user;

import java.util.ArrayList;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.mymedialist.api.exception.ValidationException;
import uk.mymedialist.api.util.ValidationResult;

@Service
public class UserValidator {

  static final String DISPLAY_NAME_FIELD = "displayName";
  static final String EMAIL_FIELD = "email";
  static final String PASSWORD_FIELD = "password";

  private final UserService userService;

  @Autowired
  UserValidator(UserService userService) {
    this.userService = userService;
  }

  void validate(CreateUserRequest createUserRequest) {
    var validationResults = new ArrayList<ValidationResult>();
    validateDisplayName(createUserRequest.displayName()).ifPresent(validationResults::add);
    validateEmail(createUserRequest.email()).ifPresent(validationResults::add);
    validatePassword(createUserRequest.password()).ifPresent(validationResults::add);

    if (!validationResults.isEmpty()) {
      throw new ValidationException(validationResults);
    }
  }

  private Optional<ValidationResult> validateDisplayName(String displayName) {
    if (StringUtils.isBlank(displayName)) {
      return Optional.of(new ValidationResult(DISPLAY_NAME_FIELD, ValidationResult.FIELD_REQUIRED));
    }

    return Optional.empty();
  }

  private Optional<ValidationResult> validateEmail(String email) {
    if (StringUtils.isBlank(email)) {
      return Optional.of(new ValidationResult(EMAIL_FIELD, ValidationResult.FIELD_REQUIRED));
    }

    if (userService.existsByEmail(email)) {
      return Optional.of(new ValidationResult(EMAIL_FIELD, "mustBeUnique"));
    }

    return Optional.empty();
  }

  private Optional<ValidationResult> validatePassword(String password) {
    if (StringUtils.isBlank(password)) {
      return Optional.of(new ValidationResult(PASSWORD_FIELD, ValidationResult.FIELD_REQUIRED));
    }

    return Optional.empty();
  }

}
