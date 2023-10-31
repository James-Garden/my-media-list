package uk.mymedialist.api.user;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;
  private final UserValidator userValidator;

  @Autowired
  UserController(UserService userService, UserValidator userValidator) {
    this.userService = userService;
    this.userValidator = userValidator;
  }

  @PostMapping
  ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
    userValidator.validate(request);
    var userId = userService.createUser(
        request.displayName(),
        request.email(),
        request.password()
    );

    return ResponseEntity
        .created(URI.create("/api/user/%s".formatted(userId)))
        // TODO: Create and return a Session ID here
        .body(userId.toString());
  }

}
