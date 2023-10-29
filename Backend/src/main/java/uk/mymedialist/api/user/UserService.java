package uk.mymedialist.api.user;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  UUID createUser(String displayName, String email, String password) {
    var encodedPassword = passwordEncoder.encode(password);
    var user = new User()
        .setDisplayName(displayName)
        .setEmail(email)
        .setPassword(encodedPassword);
    return userRepository.save(user).getId();
  }

  boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

}
