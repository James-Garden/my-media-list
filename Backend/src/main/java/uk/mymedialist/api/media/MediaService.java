package uk.mymedialist.api.media;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class MediaService {

  private final MediaRepository mediaRepository;

  @Autowired
  MediaService(MediaRepository mediaRepository) {
    this.mediaRepository = mediaRepository;
  }

  public Optional<MediaView> getMediaView(UUID mediaId) {
    return mediaRepository
        .findById(mediaId)
        .map(Media::asView);
  }

  public MediaView createMedia(MediaView mediaView) {
    var media = new Media()
        .setType(mediaView.getType())
        .setTitle(mediaView.getTitle())
        .setDescription(mediaView.getDescription())
        .setImageUrl(mediaView.getImageUrl());

    if (Type.FILM == media.getType()) {
      media.setEpisodes(1);
    } else {
      media.setEpisodes(mediaView.getEpisodes());
    }

    return mediaRepository.save(media).asView();
  }
}
