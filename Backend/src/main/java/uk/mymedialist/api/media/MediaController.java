package uk.mymedialist.api.media;

import java.net.URI;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/media")
public class MediaController {

  private final MediaService mediaService;
  private final MediaValidator mediaValidator;

  @Autowired
  MediaController(MediaService mediaService, MediaValidator mediaValidator) {
    this.mediaService = mediaService;
    this.mediaValidator = mediaValidator;
  }

  @GetMapping("/{mediaId}")
  public ResponseEntity<MediaView> getMedia(@PathVariable("mediaId") UUID mediaId) {
    return mediaService.getMediaView(mediaId)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new MediaNotFoundException(mediaId));
  }

  @PostMapping
  public ResponseEntity<MediaView> createMedia(@RequestBody MediaView inputMediaView) {
    mediaValidator.validate(inputMediaView);
    var mediaView = mediaService.createMedia(inputMediaView);

    return ResponseEntity
        .created(URI.create("/api/media/%s".formatted(mediaView.getId())))
        .body(mediaView);
  }
}
