package uk.mymedialist.api.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaView {

  static final String TITLE_FIELD = "title";
  static final String IMAGE_URL_FIELD = "imageUrl";
  static final String DESCRIPTION_FIELD = "description";
  static final String TYPE_FIELD = "type";
  static final String EPISODES_FIELD = "episodes";

  private UUID id;
  private String title;
  private String imageUrl;
  private String description;
  private Type type;
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

  public Type getType() {
    return type;
  }

  public MediaView setType(Type type) {
    this.type = type;
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
