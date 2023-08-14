package uk.mymedialist.api;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.mymedialist.api.util.AbstractIntegrationTest;

class BackendApplicationTests extends AbstractIntegrationTest {

  @Autowired
  private EntityManager entityManager;

  @Test
  void contextLoads() {
    assertThat(entityManager).isNotNull();
  }
}
