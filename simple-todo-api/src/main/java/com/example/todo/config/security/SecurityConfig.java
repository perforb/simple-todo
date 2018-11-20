package com.example.todo.config.security;

import com.example.todo.domain.user.UserRepository;
import com.example.todo.library.datetime.DateTimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.GenericFilterBean;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final Environment env;
  private final MappingJackson2HttpMessageConverter converter;
  private final DateTimeProvider provider;
  private final UserRepository repository;

  public SecurityConfig(Environment env,
                        MappingJackson2HttpMessageConverter converter,
                        DateTimeProvider provider,
                        UserRepository repository) {
    this.env = env;
    this.converter = converter;
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
      .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    ;
  }

  @Bean
  GenericFilterBean authenticationFilter() {
    String secretKey = getSecretKey();
    return new AuthenticationFilter(repository, secretKey);
  }

  @Bean
  AuthenticationEntryPoint authenticationEntryPoint() {
    return new DefaultAuthenticationEntryPoint(converter);
  }

  @Bean
  AccessDeniedHandler accessDeniedHandler() {
    return new DefaultAccessDeniedHandler();
  }

  @Bean
  AuthenticationSuccessHandler authenticationSuccessHandler() {
    String secretKey = getSecretKey();
    int expiresIn = Integer.parseInt(env.getProperty(
      "app.authorize.token.expires-in", "30"
    ));

    return new DefaultAuthenticationSuccessHandler(
      secretKey,
      expiresIn,
      converter,
      provider
    );
  }

  @Bean
  AuthenticationFailureHandler authenticationFailureHandler() {
    return new SimpleUrlAuthenticationFailureHandler();
  }

  @Bean
  LogoutSuccessHandler logoutSuccessHandler() {
    return new HttpStatusReturningLogoutSuccessHandler();
  }

  private String getSecretKey() {
    return env.getProperty("app.authorize.jwt.secret-key");
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
