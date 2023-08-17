package uk.mymedialist.api.media;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MediaServiceTest {

  @Mock
  private MediaRepository mediaRepository;

  @InjectMocks
  private MediaService mediaService;

  @Captor
  ArgumentCaptor<Media> mediaArgumentCaptor;

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
              MediaView::getType,
              MediaView::getEpisodes
          ).containsExactly(
              media.getId(),
              media.getTitle(),
              media.getDescription(),
              media.getImageUrl(),
              media.getType(),
              media.getEpisodes()
          );
    }
  }

  @Test
  void createMedia_Film() {
    var film = MediaTestUtil.film();
    var inputMediaView = film.asView();
    when(mediaRepository.save(any())).thenReturn(film);

    var outputMediaView = mediaService.createMedia(inputMediaView);

    verify(mediaRepository).save(mediaArgumentCaptor.capture());
    var savedMedia = mediaArgumentCaptor.getValue();

    assertThat(outputMediaView).extracting(
        MediaView::getId,
        MediaView::getTitle,
        MediaView::getDescription,
        MediaView::getImageUrl,
        MediaView::getType,
        MediaView::getEpisodes
    ).containsExactly(
        film.getId(),
        film.getTitle(),
        film.getDescription(),
        film.getImageUrl(),
        film.getType(),
        1
    );

    assertThat(savedMedia).extracting(
        Media::getTitle,
        Media::getDescription,
        Media::getImageUrl,
        Media::getType,
        Media::getEpisodes
    ).containsExactly(
        inputMediaView.getTitle(),
        inputMediaView.getDescription(),
        inputMediaView.getImageUrl(),
        inputMediaView.getType(),
        1
    );
  }

  @Test
  void createMedia_NotFilm() {
    var series = MediaTestUtil.series();
    var inputMediaView = series.asView();
    when(mediaRepository.save(any())).thenReturn(series);

    var outputMediaView = mediaService.createMedia(inputMediaView);

    verify(mediaRepository).save(mediaArgumentCaptor.capture());
    var savedMedia = mediaArgumentCaptor.getValue();

    assertThat(outputMediaView).extracting(
        MediaView::getId,
        MediaView::getTitle,
        MediaView::getDescription,
        MediaView::getImageUrl,
        MediaView::getType,
        MediaView::getEpisodes
    ).containsExactly(
        series.getId(),
        series.getTitle(),
        series.getDescription(),
        series.getImageUrl(),
        series.getType(),
        series.getEpisodes()
    );

    assertThat(savedMedia).extracting(
        Media::getTitle,
        Media::getDescription,
        Media::getImageUrl,
        Media::getType,
        Media::getEpisodes
    ).containsExactly(
        inputMediaView.getTitle(),
        inputMediaView.getDescription(),
        inputMediaView.getImageUrl(),
        inputMediaView.getType(),
        inputMediaView.getEpisodes()
    );
  }
}
