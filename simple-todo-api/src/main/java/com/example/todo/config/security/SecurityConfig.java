package com.example.todo.config.security;

import com.example.todo.domain.user.UserRepository;
import com.example.todo.library.datetime.DateTimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.GenericFilterBean;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final Environment env;
  private final DateTimeProvider provider;
  private final UserRepository repository;

  public SecurityConfig(Environment env,
                        DateTimeProvider provider,
                        UserRepository repository) {
    this.env = env;
    this.provider = provider;
    this.repository = repository;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .mvcMatchers("/**")
          .hasRole("USER")
        .anyRequest()
          .authenticated()
      .and()
      .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint())
        .accessDeniedHandler(accessDeniedHandler())
      .and()
      .formLogin()
        .loginProcessingUrl("/login").permitAll()
          .usernameParameter("userId")
          .passwordParameter("password")
        .successHandler(authenticationSuccessHandler())
        .failureHandler(authenticationFailureHandler())
      .and()
      .logout()
        .logoutUrl("/logout")
        .logoutSuccessHandler(logoutSuccessHandler())
      .and()
      .csrf()
        .disable()
      .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    ;
  }

  GenericFilterBean tokenFilter() {
    String secretKey = getSecretKey();
    return new TokenFilter(repository, secretKey);
  }

  AuthenticationEntryPoint authenticationEntryPoint() {
    return new DefaultAuthenticationEntryPoint();
  }

  AccessDeniedHandler accessDeniedHandler() {
    return new DefaultAccessDeniedHandler();
  }

  AuthenticationSuccessHandler authenticationSuccessHandler() {
    String secretKey = getSecretKey();
    return new DefaultAuthenticationSuccessHandler(secretKey, provider);
  }

  AuthenticationFailureHandler authenticationFailureHandler() {
    return new DefaultAuthenticationFailureHandler();
  }

  LogoutSuccessHandler logoutSuccessHandler() {
    return new HttpStatusReturningLogoutSuccessHandler();
  }

  private String getSecretKey() {
    return env.getProperty("jwt.secret-key");
  }

  @Configuration
  static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private final UserDetailsService service;

    public AuthenticationConfiguration(UserDetailsService service) {
      this.service = service;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Override
    public void init(AuthenticationManagerBuilder builder) throws Exception {
      builder
        .eraseCredentials(true)
        .userDetailsService(service)
        .passwordEncoder(passwordEncoder());
    }
  }
}
