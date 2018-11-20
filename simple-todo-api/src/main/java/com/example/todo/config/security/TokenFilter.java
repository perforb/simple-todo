package com.example.todo.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.todo.domain.user.UserDetails;
import com.example.todo.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenFilter extends GenericFilterBean {

  private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

  private final UserRepository repository;
  private final Algorithm algorithm;

  public TokenFilter(UserRepository repository, String secretKey) {
    this.repository = repository;
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
      ((HttpServletResponse) response).sendError(
        HttpStatus.UNAUTHORIZED.value(),
        HttpStatus.UNAUTHORIZED.getReasonPhrase()
      );
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
    String userId = jwt.getSubject();
    repository.findByUserId(userId).ifPresent(user -> {
      UserDetails userDetails = new UserDetails(user);
      SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities())
        );
    });
  }
}
