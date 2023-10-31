package uk.mymedialist.api.media;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uk.mymedialist.api.util.AbstractIntegrationTest;
import uk.mymedialist.api.util.TestTransactionManager;

class MediaControllerTest extends AbstractIntegrationTest {

  @Autowired
  private TestTransactionManager transactionManager;

  @Autowired
  private MediaRepository mediaRepository;

  @Nested
  class getMediaTests {

    @Test
    void getMedia() {
      var media = MediaTestUtil.film();
      transactionManager.runInTransaction(() -> entityManager.persist(media));

      var mediaViewResponseEntity = testRestTemplate.exchange(
          "/api/media/%s".formatted(media.getId()),
          HttpMethod.GET,
          HttpEntity.EMPTY,
          MediaView.class
      );

      assertThat(mediaViewResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(mediaViewResponseEntity.getBody()).extracting(
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
    @Test
    void getMedia_NotFound() {
      var mediaViewResponseEntity = testRestTemplate.exchange(
          "/api/media/%s".formatted(UUID.randomUUID()),
          HttpMethod.GET,
          HttpEntity.EMPTY,
          String.class
      );

      assertThat(mediaViewResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
  }

  @Nested
  class createMediaTests {

    @Test
    void createMedia() {
      var mediaView = MediaTestUtil.series().asView();

      var mediaViewResponseEntity = testRestTemplate.exchange(
          "/api/media",
          HttpMethod.POST,
          new HttpEntity<>(mediaView),
          MediaView.class
      );

      assertThat(mediaViewResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
      assertThat(mediaViewResponseEntity.getBody()).isNotNull();

      var mediaId = mediaViewResponseEntity.getBody().getId();
      assertThat(mediaViewResponseEntity.getBody().getId()).isNotNull();
      assertThat(mediaViewResponseEntity.getHeaders().getLocation())
          .isEqualTo(URI.create("/api/media/%s".formatted(mediaId)));

      assertThat(mediaViewResponseEntity.getBody()).extracting(
          MediaView::getTitle,
          MediaView::getDescription,
          MediaView::getImageUrl,
          MediaView::getType,
          MediaView::getEpisodes
      ).containsExactly(
          mediaView.getTitle(),
          mediaView.getDescription(),
          mediaView.getImageUrl(),
          mediaView.getType(),
          mediaView.getEpisodes()
      );

      var mediaEntity = mediaRepository.findById(mediaId);

      assertThat(mediaEntity).isNotEmpty();
      assertThat(mediaEntity.get()).extracting(
          Media::getCreationTimestamp,
          Media::getUpdateTimestamp
      ).doesNotContainNull();
    }

    @Test
    void createMedia_ValidationError() {
      var mediaView = new MediaView();
      var currentMediaCount = mediaRepository.count();

      var mediaViewResponseEntity = testRestTemplate.exchange(
          "/api/media",
          HttpMethod.POST,
          new HttpEntity<>(mediaView),
          Object.class
      );

      assertThat(mediaViewResponseEntity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
      assertThat(mediaRepository.count()).isEqualTo(currentMediaCount);
    }
  }
}
