package com.example.todo.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

  private static final Logger log = LoggerFactory.getLogger(DefaultAccessDeniedHandler.class);

  private final MappingJackson2HttpMessageConverter converter;

  public DefaultAccessDeniedHandler(MappingJackson2HttpMessageConverter converter) {
    this.converter = converter;
  }

  @Override
  public void handle(HttpServletRequest req,
                     HttpServletResponse res,
                     AccessDeniedException exception) throws IOException {

    if (res.isCommitted()) {
      log.debug("The response has already been committed.");
      return;
    }

    res.setStatus(HttpStatus.FORBIDDEN.value());

    AuthorizationResponse response = new AuthorizationResponse(
      "Unauthorized",
      "An authorization request is rejected because the roles are not granted."
    );

    HttpOutputMessage message = new ServletServerHttpResponse(res);
    converter.write(response, MediaType.APPLICATION_JSON, message);
  }
}
