package com.example.todo.config.security;

public class AuthorizationResponse {

  private final String error;
  private final String error_description;

  public AuthorizationResponse(
    String error,
    String errorDescription
  ) {
    this.error = error;
    this.error_description = errorDescription;
  }

  public String getError() {
    return error;
  }

  public String getError_description() {
    return error_description;
  }
}
