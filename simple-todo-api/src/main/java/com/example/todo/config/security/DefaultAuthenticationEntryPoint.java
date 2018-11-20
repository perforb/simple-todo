package com.example.todo.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationEntryPoint.class);

  private final MappingJackson2HttpMessageConverter converter;

  public DefaultAuthenticationEntryPoint(MappingJackson2HttpMessageConverter converter) {
    this.converter = converter;
  }

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException exception) throws IOException {

    if (response.isCommitted()) {
      log.debug("The response has already been committed.");
      return;
    }

    AuthenticationResponse tokenResponse = new AuthenticationResponse(
      "",
      "",
      "",
      0,
      "",
      "Unauthenticated",
      "An authentication request is rejected because the credentials are invalid."
    );

    HttpOutputMessage message = new ServletServerHttpResponse(response);
    converter.write(tokenResponse, MediaType.APPLICATION_JSON_UTF8, message);
  }
}
