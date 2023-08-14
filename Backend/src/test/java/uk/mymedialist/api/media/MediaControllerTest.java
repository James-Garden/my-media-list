package uk.mymedialist.api.media;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
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

  @Test
  void getMedia_NotFound() {
    var mediaViewResponseEntity = testRestTemplate.exchange(
        "/api/media/%s".formatted(UUID.randomUUID()),
        HttpMethod.GET,
        HttpEntity.EMPTY,
        MediaView.class
    );

    assertThat(mediaViewResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
