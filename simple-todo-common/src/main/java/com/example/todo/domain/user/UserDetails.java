package com.example.todo.domain.user;

import org.springframework.security.core.authority.AuthorityUtils;

public class UserDetails extends org.springframework.security.core.userdetails.User {

  private final User user;

  public UserDetails(User user, String... roles) {
    super(
      user.getUserId(),
      user.getHashedPassword(),
      AuthorityUtils.createAuthorityList(roles)
    );
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
