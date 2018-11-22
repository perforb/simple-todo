package com.example.todo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.todo.domain.user.UserDetails;
import com.example.todo.library.datetime.DateTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationSuccessHandler.class);

  private final Algorithm algorithm;
  private final int expiresIn;
  private final MappingJackson2HttpMessageConverter converter;
  private final DateTimeProvider provider;

  public DefaultAuthenticationSuccessHandler(
    String secretKey,
    int expiresIn,
    MappingJackson2HttpMessageConverter converter,
    DateTimeProvider provider
  ) {
    this.algorithm = Algorithm.HMAC512(secretKey);
    this.expiresIn = expiresIn;
    this.converter = converter;
    this.provider = provider;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest req,
                                      HttpServletResponse res,
                                      Authentication auth) throws IOException {

    if (res.isCommitted()) {
      log.debug("The response has already been committed.");
      return;
    }

    String accessToken = generateToken(auth);
    AuthenticationResponse response = new AuthenticationResponse(
      accessToken,
      "",
      "Bearer",
      expiresIn,
      "",
      "",
      ""
    );

    HttpOutputMessage message = new ServletServerHttpResponse(res);
    converter.write(response, MediaType.APPLICATION_JSON_UTF8, message);
    res.setStatus(HttpStatus.OK.value());
  }

  private String generateToken(Authentication auth) {
    long expirationTime = TimeUnit.MINUTES.toMillis(expiresIn);
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    Date issuedAt = Date.from(provider.instant());
    Date notBefore = new Date(issuedAt.getTime());
    Date expiresAt = new Date(issuedAt.getTime() + expirationTime);
    String token = JWT.create()
      .withIssuedAt(issuedAt)
      .withNotBefore(notBefore)
      .withExpiresAt(expiresAt)
      .withSubject(userDetails.getUser().getId().toString())
      .sign(algorithm);

    log.debug("generated token: {}", token);
    return token;
  }
}
