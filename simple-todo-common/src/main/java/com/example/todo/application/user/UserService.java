package com.example.todo.application.user;

import com.example.todo.domain.user.UserDetails;
import com.example.todo.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String userId) {
    return repository.findByUserId(userId)
      .map(user -> new UserDetails(user, "ROLE_USER"))
      .orElseThrow(() -> new UsernameNotFoundException(String.format(
        "The user with the given user id [%s] was not found.",
        userId
      )));
  }
}
