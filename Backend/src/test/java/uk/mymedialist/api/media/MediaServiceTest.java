package uk.mymedialist.api.media;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

  @Mock
  private MediaRepository mediaRepository;

  @InjectMocks
  private MediaService mediaService;

  @Nested
  class getMediaViewTests {

    private static final UUID MEDIA_ID = UUID.randomUUID();

    @Test
    void whenNotFound_AssertEmpty() {
      when(mediaRepository.findById(MEDIA_ID)).thenReturn(Optional.empty());

      var mediaViewOptional = mediaService.getMediaView(MEDIA_ID);

      assertThat(mediaViewOptional).isEmpty();
    }

    @Test
    void whenFound_AssertView() {
      var media = MediaTestUtil.filmWithId();

      when(mediaRepository.findById(MEDIA_ID)).thenReturn(Optional.of(media));

      var mediaViewOptional = mediaService.getMediaView(MEDIA_ID);

      assertThat(mediaViewOptional).isNotEmpty();
      assertThat(mediaViewOptional.get())
          .extracting(
              MediaView::getId,
              MediaView::getTitle,
              MediaView::getDescription,
              MediaView::getImageUrl,
              MediaView::getMediaType,
              MediaView::getEpisodes
          ).containsExactly(
              media.getId(),
              media.getTitle(),
              media.getDescription(),
              media.getImageUrl(),
              media.getMediaType(),
              media.getEpisodes()
          );
    }
  }
}
