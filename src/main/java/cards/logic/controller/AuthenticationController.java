package cards.logic.controller;

import cards.logic.dto.JwtAuthenticationResponse;
import cards.logic.dto.LoginRequestDto;
import cards.logic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling authentication requests.
 * It provides an endpoint for user authentication, issuing JWTs upon successful authentication.
 */
@RestController
public class AuthenticationController implements AuthorizationAPI {

  private final TokenService tokenService;
  public final AuthenticationManager authenticationManager;

  public AuthenticationController(TokenService tokenService, AuthenticationManager authenticationManager) {
    this.tokenService = tokenService;
    this.authenticationManager = authenticationManager;
  }

  /**
   * Authenticates a user based on username and password, issuing a JWT upon successful authentication.
   *
   * @param loginRequest Data transfer object containing login credentials.
   * @return A {@link ResponseEntity} containing the JWT or an error message if authentication fails.
   */
  @Override
  public ResponseEntity<JwtAuthenticationResponse> authenticateUser(LoginRequestDto loginRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwtToken = tokenService.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
  }
}
