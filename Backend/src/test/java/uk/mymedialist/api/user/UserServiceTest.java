package uk.mymedialist.api.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  @Captor
  private ArgumentCaptor<User> argumentCaptor;

  @Test
  void createUser() {
    var displayName = "Frodo";
    var email = "frodo.baggins@email.com";
    var password = "HobbitsRule123";
    var encodedPassword = "{bcrypt}$ba$11$AfRVdPVzMx6LxZTzeYjbA.hvGYc23L/ajBcCffDN91yXwfJlkYFlW";

    when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
    when(userRepository.save(any())).thenReturn(new User());

    userService.createUser(displayName, email, password);

    verify(userRepository).save(argumentCaptor.capture());

    assertThat(argumentCaptor.getValue()).extracting(
        User::getDisplayName, User::getEmail, User::getPassword
    ).containsExactly(
      displayName, email, encodedPassword
    );
  }

  @ParameterizedTest
  @ValueSource(booleans = { true, false })
  void existsByEmail(boolean returnValue) {
    var email = "test.email@example.com";

    when(userRepository.existsByEmail(email)).thenReturn(returnValue);

    var doesUserExist = userService.existsByEmail(email);

    assertThat(doesUserExist).isEqualTo(returnValue);
  }

}
