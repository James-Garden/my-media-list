package uk.mymedialist.api;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class BackendApplicationTests extends AbstractIntegrationTest {

  @Autowired
  private EntityManager entityManager;

  @Test
  void contextLoads() {
    assertThat(entityManager).isNotNull();
  }
}
