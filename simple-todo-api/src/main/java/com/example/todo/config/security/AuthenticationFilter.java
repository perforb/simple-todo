package com.example.todo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.todo.application.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends GenericFilterBean {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

  private final UserService service;
  private final MappingJackson2HttpMessageConverter converter;
  private final Algorithm algorithm;

  public AuthenticationFilter(
    UserService service,
    MappingJackson2HttpMessageConverter converter,
    String secretKey
  ) {
    this.service = service;
    this.converter = converter;
    this.algorithm = Algorithm.HMAC512(secretKey);
  }

  @Override
  public void doFilter(
    ServletRequest request,
    ServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {

    String token = getToken(request);

    if (token.isEmpty()) {
      chain.doFilter(request, response);
      return;
    }

    try {
      DecodedJWT decodedJWT = verifyToken(token);
      setAuthentication(decodedJWT);
    } catch (JWTVerificationException e) {
      log.error("Failed to verify the token. [token: {}]", token);
      SecurityContextHolder.clearContext();
      writeUnauthenticatedResponse((HttpServletResponse) response);
      return;
    }

    chain.doFilter(request, response);
  }

  private String getToken(ServletRequest request) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    return Optional.ofNullable((httpRequest).getHeader(HttpHeaders.AUTHORIZATION))
      .map(token -> token.replaceAll("Bearer ", ""))
      .orElse("");
  }

  private DecodedJWT verifyToken(String token) {
    JWTVerifier verifier = JWT.require(algorithm).build();
    return verifier.verify(token);
  }

  private void setAuthentication(DecodedJWT jwt) {
    long id = Long.parseLong(jwt.getSubject());
    UserDetails user = service.findById(id);
    SecurityContextHolder.getContext()
      .setAuthentication(new UsernamePasswordAuthenticationToken(
        user,
        null,
        user.getAuthorities()
      ));
  }

  private void writeUnauthenticatedResponse(HttpServletResponse res) throws IOException {
    res.setStatus(HttpStatus.UNAUTHORIZED.value());

    AuthenticationResponse response = new AuthenticationResponse(
      "",
      "",
      "",
      0,
      "",
      "Unauthenticated",
      "An authentication request is rejected because the credentials are invalid."
    );

    HttpOutputMessage message = new ServletServerHttpResponse(res);
    converter.write(response, MediaType.APPLICATION_JSON_UTF8, message);
  }
}
