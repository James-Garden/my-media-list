package uk.mymedialist.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import uk.mymedialist.api.util.AbstractIntegrationTest;
import uk.mymedialist.api.util.ValidationResult;

class UserControllerTest extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setup() {
    var users = userRepository.findAll();
    userRepository.deleteAll(users);
  }

  @Nested
  class CreateUserTests {

    @Test
    void createUser() {
      var request = new CreateUserRequest(
          "John Smith", "jsmith@email.com", "password"
      );
      var response = testRestTemplate.exchange(
          "/api/user",
          HttpMethod.POST,
          new HttpEntity<>(request),
          String.class
      );

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

      var users = userRepository.findAll();

      assertThat(users).hasSize(1);
      var newUser = users.iterator().next();

      assertThat(response.getHeaders().getLocation())
          .asString()
          .contains(newUser.getId().toString());

      assertThat(newUser).extracting(
          User::getId,
          User::getCreationTimestamp,
          User::getUpdateTimestamp
      ).doesNotContainNull();

      assertThat(newUser).extracting(
          User::getDisplayName,
          User::getEmail
      ).containsExactly(
          request.displayName(), request.email()
      );

      assertThat(newUser.getPassword()).doesNotContain(request.password());
    }

    @Test
    void createUser_InvalidRequest_AssertNotCreated() {
      var request = new CreateUserRequest(
          "", "", ""
      );
      var response = testRestTemplate.exchange(
          "/api/user",
          HttpMethod.POST,
          new HttpEntity<>(request),
          new ParameterizedTypeReference<List<ValidationResult>>() {}
      );

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
      assertThat(response.getBody()).extracting(
          ValidationResult::field,
          ValidationResult::error
      ).containsExactly(
          tuple(UserValidator.DISPLAY_NAME_FIELD, ValidationResult.FIELD_REQUIRED),
          tuple(UserValidator.EMAIL_FIELD, ValidationResult.FIELD_REQUIRED),
          tuple(UserValidator.PASSWORD_FIELD, ValidationResult.FIELD_REQUIRED)
      );

      var users = userRepository.findAll();
      assertThat(users).isEmpty();
    }

    @Test
    void createUser_UserWithEmailAlreadyExists_AssertNotCreated() {
      var email = "dan.smith@email.com";
      var existingUser = new User().setEmail(email);
      userRepository.save(existingUser);

      var request = new CreateUserRequest(
          "John Smith", email, "password"
      );
      var response = testRestTemplate.exchange(
          "/api/user",
          HttpMethod.POST,
          new HttpEntity<>(request),
          new ParameterizedTypeReference<List<ValidationResult>>() {}
      );

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
      assertThat(response.getBody()).extracting(
          ValidationResult::field,
          ValidationResult::error
      ).containsExactly(
          tuple(UserValidator.EMAIL_FIELD, "mustBeUnique")
      );

      var users = userRepository.findAll();
      assertThat(users).hasSize(1);
    }
  }

}
