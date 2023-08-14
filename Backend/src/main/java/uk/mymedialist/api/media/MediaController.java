package uk.mymedialist.api.media;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/media")
public class MediaController {

  private final MediaService mediaService;

  @Autowired
  public MediaController(MediaService mediaService) {
    this.mediaService = mediaService;
  }

  @GetMapping("/{mediaId}")
  public ResponseEntity<MediaView> getMedia(@PathVariable("mediaId") UUID mediaId) {
    return mediaService.getMediaView(mediaId)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new MediaNotFoundException(mediaId));
  }
}
