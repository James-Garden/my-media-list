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
}
