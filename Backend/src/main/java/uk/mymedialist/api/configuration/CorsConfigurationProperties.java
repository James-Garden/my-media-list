package uk.mymedialist.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cors")
public final class CorsConfigurationProperties {

  private String[] allowedOrigins;

  public String[] getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(String[] allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }
}
