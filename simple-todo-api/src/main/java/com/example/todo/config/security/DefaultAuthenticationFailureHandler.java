package com.example.todo.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationFailureHandler.class);

  @Override
  public void onAuthenticationFailure(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException exception
  ) throws IOException {

    if (response.isCommitted()) {
      log.debug("The response has already been committed.");
      return;
    }

    response.sendError(
      HttpStatus.FORBIDDEN.value(),
      HttpStatus.FORBIDDEN.getReasonPhrase()
    );
  }
}
