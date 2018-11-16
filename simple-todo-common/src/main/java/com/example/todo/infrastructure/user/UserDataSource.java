package com.example.todo.infrastructure.user;

import com.example.todo.domain.user.User;
import com.example.todo.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDataSource implements UserRepository {

  private final UserMapper mapper;

  public UserDataSource(UserMapper mapper) {
    this.mapper = mapper;
  }

  public Optional<User> findById(Long id) {
    return Optional.ofNullable(mapper.findById(id));
  }

  public Optional<User> findByUserId(String userId) {
    return Optional.ofNullable(mapper.findByUserId(userId));
  }
}
