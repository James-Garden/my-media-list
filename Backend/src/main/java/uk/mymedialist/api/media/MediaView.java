package uk.mymedialist.api.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaView {

  private UUID id;
  private String title;
  private String imageUrl;
  private String description;
  private MediaType mediaType;
  private Integer episodes;

  public UUID getId() {
    return id;
  }

  public MediaView setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public MediaView setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public MediaView setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public MediaView setDescription(String description) {
    this.description = description;
    return this;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public MediaView setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
    return this;
  }

  public Integer getEpisodes() {
    return episodes;
  }

  public MediaView setEpisodes(Integer episodes) {
    this.episodes = episodes;
    return this;
  }
}
