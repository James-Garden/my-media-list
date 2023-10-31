package uk.mymedialist.api.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @CreationTimestamp
  private ZonedDateTime creationTimestamp;

  @UpdateTimestamp
  private ZonedDateTime updateTimestamp;

  private String displayName;

  private String email;

  private String password;

  public UUID getId() {
    return id;
  }

  public ZonedDateTime getCreationTimestamp() {
    return creationTimestamp;
  }

  public ZonedDateTime getUpdateTimestamp() {
    return updateTimestamp;
  }

  public String getDisplayName() {
    return displayName;
  }

  public User setDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }
}
