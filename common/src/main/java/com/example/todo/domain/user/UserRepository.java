package com.example.todo.domain.user;

import java.util.Optional;

public interface UserRepository {

  Optional<User> findById(Long id);

  Optional<User> findByUserId(String userId);
}
