package com.example.empuser.security;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {
  private final CustomUserDetailsService uds; private final JwtUtil jwtUtil;
  public SecurityConfig(CustomUserDetailsService uds, JwtUtil jwtUtil){ this.uds=uds; this.jwtUtil=jwtUtil; }
  @Bean public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder) throws Exception { return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(uds).passwordEncoder(encoder).and().build(); }
  @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    JwtFilter jwtFilter = new JwtFilter(jwtUtil, uds);
    http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authorizeHttpRequests().requestMatchers("/api/auth/**").permitAll()
      .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
      .requestMatchers("/api/employee/**").hasAuthority("ROLE_EMPLOYEE")
      .anyRequest().authenticated().and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    http.headers().frameOptions().disable();
    return http.build();
  }
  @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
}