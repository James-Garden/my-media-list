package uk.mymedialist.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.mymedialist.api.util.ValidatorTestUtil.getValidationErrors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.mymedialist.api.util.ValidationResult;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserValidator userValidator;

  @Test
  void validate_ValidUser_AssertDoesNotThrow() {
    var validRequest = new CreateUserRequest(
        "Bilbo Baggins",
        "b.baggings@email.com",
        "Password123"
    );

    when(userService.existsByEmail(validRequest.email())).thenReturn(false);

    assertThatNoException().isThrownBy(() -> userValidator.validate(validRequest));

    verifyNoMoreInteractions(userService);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = "   ")
  void validate_EmptyRequest_AssertErrors(String nullEmptyOrBlankString) {
    var emptyRequest = new CreateUserRequest(
        nullEmptyOrBlankString, nullEmptyOrBlankString, nullEmptyOrBlankString
    );

    var errors = getValidationErrors(() -> userValidator.validate(emptyRequest));

    assertThat(errors).extracting(
        ValidationResult::field,
        ValidationResult::error
    ).containsExactly(
        tuple(UserValidator.DISPLAY_NAME_FIELD, ValidationResult.FIELD_REQUIRED),
        tuple(UserValidator.EMAIL_FIELD, ValidationResult.FIELD_REQUIRED),
        tuple(UserValidator.PASSWORD_FIELD, ValidationResult.FIELD_REQUIRED)
    );

    verifyNoInteractions(userService);
  }

  @Test
  void validate_ExistingEmailAddress_AssertErrors() {
    var duplicateRequest = new CreateUserRequest(
        "Bilbo Baggins",
        "b.baggings@email.com",
        "Password123"
    );

    when(userService.existsByEmail(duplicateRequest.email())).thenReturn(true);

    var errors = getValidationErrors(() -> userValidator.validate(duplicateRequest));

    assertThat(errors).extracting(
        ValidationResult::field,
        ValidationResult::error
    ).containsExactly(
        tuple(UserValidator.EMAIL_FIELD, "mustBeUnique")
    );

    verifyNoMoreInteractions(userService);
  }

}
