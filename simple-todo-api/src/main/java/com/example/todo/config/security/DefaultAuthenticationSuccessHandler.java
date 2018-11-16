package com.example.todo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todo.domain.user.UserDetails;
import com.example.todo.library.datetime.DateTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationSuccessHandler.class);
  private static final Long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10L);

  private final Algorithm algorithm;
  private final DateTimeProvider provider;

  public DefaultAuthenticationSuccessHandler(
    String secretKey,
    DateTimeProvider provider
  ) {
    this.algorithm = Algorithm.HMAC512(secretKey);
    this.provider = provider;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication auth) {

    if (response.isCommitted()) {
      log.debug("The response has already been committed.");
      return;
    }

    String token = generateToken(auth);
    setToken(response, token);
    response.setStatus(HttpStatus.OK.value());
  }

  private String generateToken(Authentication auth) {
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    Date issuedAt = Date.from(provider.instant());
    Date notBefore = new Date(issuedAt.getTime());
    Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);
    String token = JWT.create()
      .withIssuedAt(issuedAt)
      .withNotBefore(notBefore)
      .withExpiresAt(expiresAt)
      .withSubject(userDetails.getUser().getId().toString())
      .sign(algorithm);

    log.debug("generated token is {}", token);
    return token;
  }

  private void setToken(HttpServletResponse response, String token) {
    response.setHeader(
      HttpHeaders.AUTHORIZATION,
      String.format("Bearer %s", token)
    );
  }
}
