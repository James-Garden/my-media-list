package uk.mymedialist.api.media;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface MediaRepository extends CrudRepository<Media, UUID> {
}
