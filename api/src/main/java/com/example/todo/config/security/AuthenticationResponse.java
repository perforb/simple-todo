package com.example.todo.config.security;

public class AuthenticationResponse {

  private final String access_token;
  private final String refresh_token;
  private final String token_type;
  private final int expires_in;
  private final String scope;
  private final String error;
  private final String error_description;

  public AuthenticationResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    int expiresIn,
    String scope,
    String error,
    String errorDescription
  ) {
    this.access_token = accessToken;
    this.refresh_token = refreshToken;
    this.token_type = tokenType;
    this.expires_in = expiresIn;
    this.scope = scope;
    this.error = error;
    this.error_description = errorDescription;
  }

  public String getAccess_token() {
    return access_token;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public int getExpires_in() {
    return expires_in;
  }

  public String getScope() {
    return scope;
  }

  public String getError() {
    return error;
  }

  public String getError_description() {
    return error_description;
  }
}
