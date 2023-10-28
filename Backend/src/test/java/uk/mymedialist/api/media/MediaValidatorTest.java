package uk.mymedialist.api.media;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static uk.mymedialist.api.util.ValidatorTestUtil.getValidationErrors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.mymedialist.api.util.ValidationResult;

@ExtendWith(MockitoExtension.class)
class MediaValidatorTest {

  @InjectMocks
  private MediaValidator mediaValidator;

  @Test
  void validate_ValidView_AssertDoesNotThrow() {
    var validView = getValidMediaView();

    assertThatNoException().isThrownBy(() -> mediaValidator.validate(validView));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void validate_EmptyView_AssertErrors(String nullOrEmptyString) {
    var view = new MediaView()
        .setTitle(nullOrEmptyString)
        .setDescription(nullOrEmptyString)
        .setImageUrl(nullOrEmptyString);

    var validationResults = getValidationErrors(
        () -> mediaValidator.validate(view)
    );

    assertThat(validationResults).extracting(
        ValidationResult::field,
        ValidationResult::error
    ).containsExactly(
        tuple(MediaView.TITLE_FIELD, ValidationResult.FIELD_REQUIRED),
        tuple(MediaView.IMAGE_URL_FIELD, ValidationResult.FIELD_REQUIRED),
        tuple(MediaView.DESCRIPTION_FIELD, ValidationResult.FIELD_REQUIRED),
        tuple(MediaView.TYPE_FIELD, ValidationResult.FIELD_REQUIRED)
    );
  }

  @Test
  void validate_InvalidImageUrl_AssertError() {
    var view = getValidMediaView()
        .setImageUrl("not a URL");

    var validationResults = getValidationErrors(
        () -> mediaValidator.validate(view)
    );

    assertThat(validationResults).extracting(
        ValidationResult::field,
        ValidationResult::error
    ).containsExactly(
        tuple(MediaView.IMAGE_URL_FIELD, ValidationResult.FIELD_INVALID)
    );
  }

  @Test
  void validate_TypeFilmNoEpisodes_AssertDoesNotThrow() {
    var view = getValidMediaView()
        .setEpisodes(null)
        .setType(Type.FILM);

    assertDoesNotThrow(() -> mediaValidator.validate(view));
  }

  @Test
  void validate_TypeNotFilmNoEpisodes_AssertErrors() {
    var view = getValidMediaView()
        .setEpisodes(null)
        .setType(Type.SERIES);

    var validationResults = getValidationErrors(() -> mediaValidator.validate(view));

    assertThat(validationResults).extracting(
        ValidationResult::field,
        ValidationResult::error
    ).containsExactly(
        tuple(MediaView.EPISODES_FIELD, ValidationResult.FIELD_REQUIRED)
    );
  }

  @Test
  void validate_TypeNotFilm_AssertDoesNotThrow() {
    var view = getValidMediaView()
        .setEpisodes(25)
        .setType(Type.SERIES);

    assertDoesNotThrow(() -> mediaValidator.validate(view));
  }

  private static MediaView getValidMediaView() {
    return MediaTestUtil.film().asView();
  }
}
