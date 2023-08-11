package uk.mymedialist.api.media;

import java.util.UUID;
import org.springframework.security.util.FieldUtils;

public class MediaTestUtil {

  private MediaTestUtil() {
  }

  public static Media filmWithId() {
    var media = film();
    FieldUtils.setProtectedFieldValue("id", media, UUID.randomUUID());
    return media;
  }

  public static Media film() {
    var media = new Media();
    media.setTitle("Test title");
    media.setDescription("Test description");
    media.setImageUrl("www.some-website.com/example");
    media.setMediaType(MediaType.FILM);
    media.setEpisodes(1);
    return media;
  }
}
