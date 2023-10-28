package uk.mymedialist.api.util;

import java.net.URL;
import java.util.Optional;

public class UrlUtil {

  private UrlUtil() {
  }

  public static Optional<URL> toUrl(String inputUrl) {
    try {
      var outputUrl = new URL(inputUrl);
      outputUrl.toURI();
      return Optional.of(outputUrl);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
