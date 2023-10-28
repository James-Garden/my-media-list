package uk.mymedialist.api.media;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uk.mymedialist.api.exception.ValidationException;
import uk.mymedialist.api.util.UrlUtil;
import uk.mymedialist.api.util.ValidationResult;

@Service
class MediaValidator {

  void validate(MediaView mediaView) {
    var validationResults = new ArrayList<ValidationResult>();
    validateTitle(mediaView.getTitle())
        .ifPresent(validationResults::add);
    validateImageUrl(mediaView.getImageUrl())
        .ifPresent(validationResults::add);
    validateDescription(mediaView.getDescription())
        .ifPresent(validationResults::add);
    validateType(mediaView.getType())
        .ifPresent(validationResults::add);
    validateEpisodes(mediaView.getEpisodes(), mediaView.getType())
        .ifPresent(validationResults::add);
    if (!validationResults.isEmpty()) {
      throw new ValidationException(validationResults);
    }
  }

  private Optional<ValidationResult> validateTitle(String title) {
    if (StringUtils.isBlank(title)) {
      return ValidationResult.optional(MediaView.TITLE_FIELD, ValidationResult.FIELD_REQUIRED);
    }

    return Optional.empty();
  }

  private Optional<ValidationResult> validateImageUrl(String imageUrl) {
    if (StringUtils.isBlank(imageUrl)) {
      return ValidationResult.optional(MediaView.IMAGE_URL_FIELD, ValidationResult.FIELD_REQUIRED);
    }

    var url = UrlUtil.toUrl(imageUrl);
    if (url.isEmpty()) {
      return ValidationResult.optional(MediaView.IMAGE_URL_FIELD, ValidationResult.FIELD_INVALID);
    }

    return Optional.empty();
  }

  private Optional<ValidationResult> validateDescription(String description) {
    if (StringUtils.isBlank(description)) {
      return ValidationResult.optional(MediaView.DESCRIPTION_FIELD, ValidationResult.FIELD_REQUIRED);
    }

    return Optional.empty();
  }

  private Optional<ValidationResult> validateType(Type type) {
    if (Objects.isNull(type)) {
      return ValidationResult.optional(MediaView.TYPE_FIELD, ValidationResult.FIELD_REQUIRED);
    }

    return Optional.empty();
  }

  private Optional<ValidationResult> validateEpisodes(Integer episodes, Type type) {
    if (Objects.isNull(type) || Type.FILM == type) {
      return Optional.empty();
    }

    if (Objects.isNull(episodes)) {
      return ValidationResult.optional(MediaView.EPISODES_FIELD, ValidationResult.FIELD_REQUIRED);
    }

    return Optional.empty();
  }

}
