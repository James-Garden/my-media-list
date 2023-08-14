package uk.mymedialist.api.media;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "media")
public class Media {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @CreationTimestamp
  private Instant creationTimestamp;

  @UpdateTimestamp
  private Instant updateTimestamp;

  private String title;

  private String imageUrl;

  private String description;

  @Enumerated(EnumType.STRING)
  private MediaType mediaType;

  private Integer episodes;

  public MediaView asView() {
    return new MediaView()
        .setId(id)
        .setTitle(title)
        .setImageUrl(imageUrl)
        .setDescription(description)
        .setMediaType(mediaType)
        .setEpisodes(episodes);
  }

  public UUID getId() {
    return id;
  }

  public Instant getCreationTimestamp() {
    return creationTimestamp;
  }

  public Instant getUpdateTimestamp() {
    return updateTimestamp;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public Integer getEpisodes() {
    return episodes;
  }

  public void setEpisodes(Integer episodes) {
    this.episodes = episodes;
  }
}
