package cards.logic.config;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Security configuration class for Cards application.
 * Defines the security filter chain, authentication manager, and JWT token processing.
 */
@Configuration
@EnableWebSecurity
public class CardsSecurityConfig {

  private RSAKey rsaKey;

  private static final String[] SWAGGER_AUTH_WHITELIST = {
          "/api/v1/auth/**",
          "/v3/api-docs/**",
          "/v3/api-docs.yaml",
          "/swagger-ui/**",
          "/swagger-ui.html"};

  /**
   * Configures the HttpSecurity to set up CORS, CSRF, session management,
   * and the rules for protected resources.
   *
   * @param http the HttpSecurity to configure
   * @return the configured SecurityFilterChain
   * @throws Exception on configuration error
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests( auth -> auth
                    .requestMatchers("/api/login/token").permitAll()
                    .requestMatchers(SWAGGER_AUTH_WHITELIST).permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .httpBasic(Customizer.withDefaults())
            .build();
  }

  /**
   * Creates an AuthenticationManager bean to manage authentication.
   *
   * @param userDetailsService the UserDetailsService to use
   * @return the AuthenticationManager instance
   */
  @Bean
  public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(daoAuthenticationProvider);
  }

  /**
   * Configures a UserDetailsService to load user-specific data.
   *
   * @param dataSource the DataSource to use for querying user details
   * @return the configured UserDetailsService
   */
  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
    jdbcUserDetailsManager.setUsersByUsernameQuery("select email,password,enabled from users where email = ?");
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select email,authority from authorities where email = ?");
    return jdbcUserDetailsManager;
  }

  /**
   * Provides a password encoder bean to encode and decode passwords.
   *
   * @return the PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures a JWKSource to use RSA keys for JWT encoding and decoding.
   *
   * @return the JWKSource
   */
  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    rsaKey = Jwks.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  /**
   * Creates a JwtEncoder bean for encoding JWTs.
   *
   * @param securityContextJWKSource the JWKSource to use
   * @return the JwtEncoder
   */
  @Bean
  public JwtEncoder jwtEncoder(JWKSource<SecurityContext> securityContextJWKSource) {
    return new NimbusJwtEncoder(securityContextJWKSource);
  }

  /**
   * Configures a JwtDecoder bean for decoding JWTs using the RSA public key.
   *
   * @return the JwtDecoder
   * @throws JOSEException if there's an issue with the key during decoder setup
   */
  @Bean
  JwtDecoder jwtDecoder() throws JOSEException {
    return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
  }

}
