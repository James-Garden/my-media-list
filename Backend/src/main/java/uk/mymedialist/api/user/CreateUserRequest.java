package uk.mymedialist.api.user;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateUserRequest(String displayName, String email, String password) {

}
