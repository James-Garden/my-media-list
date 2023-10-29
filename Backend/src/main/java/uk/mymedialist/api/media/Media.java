package uk.mymedialist.api.media;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
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
  private ZonedDateTime creationTimestamp;

  @UpdateTimestamp
  private ZonedDateTime updateTimestamp;

  private String title;

  private String imageUrl;

  private String description;

  @Enumerated(EnumType.STRING)
  private Type type;

  private Integer episodes;

  public MediaView asView() {
    return new MediaView()
        .setId(id)
        .setTitle(title)
        .setImageUrl(imageUrl)
        .setDescription(description)
        .setType(type)
        .setEpisodes(episodes);
  }

  public UUID getId() {
    return id;
  }

  public ZonedDateTime getCreationTimestamp() {
    return creationTimestamp;
  }

  public ZonedDateTime getUpdateTimestamp() {
    return updateTimestamp;
  }

  public String getTitle() {
    return title;
  }

  public Media setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Media setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Media setDescription(String description) {
    this.description = description;
    return this;
  }

  public Type getType() {
    return type;
  }

  public Media setType(Type type) {
    this.type = type;
    return this;
  }

  public Integer getEpisodes() {
    return episodes;
  }

  public void setEpisodes(Integer episodes) {
    this.episodes = episodes;
  }
}
