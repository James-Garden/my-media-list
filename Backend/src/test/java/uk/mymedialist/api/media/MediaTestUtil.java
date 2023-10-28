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
    var media = untypedMedia();
    media.setType(Type.FILM);
    media.setEpisodes(1);
    return media;
  }

  public static Media series() {
    var media = untypedMedia();
    media.setType(Type.SERIES);
    media.setEpisodes(25);
    return media;
  }

  private static Media untypedMedia() {
    return new Media()
        .setTitle("Test title")
        .setDescription("Test description")
        .setImageUrl("https://www.some-website.com/example.png");
  }
}
