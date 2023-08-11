package uk.mymedialist.api.media;

import java.util.UUID;
import uk.mymedialist.api.exception.EntityNotFoundException;

public class MediaNotFoundException extends EntityNotFoundException {

  public MediaNotFoundException(String message) {
    super(message);
  }

  public MediaNotFoundException(UUID mediaId) {
    this("Could not find Media with ID: %s".formatted(mediaId.toString()));
  }
}
