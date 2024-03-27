package cards.logic.dto;

/**
 * A record for encapsulating the authentication response, including the JWT token and its type.
 * This response structure is typically used for sending back to the client upon successful authentication,
 * providing the necessary token for accessing secured resources.
 *
 * @param jwtToken The JWT token issued after successful authentication.
 * @param tokenType The type of the token, usually "Bearer", indicating how the token should be presented in subsequent requests.
 */
public record JwtAuthenticationResponse(String jwtToken, String tokenType) {
  public JwtAuthenticationResponse(String jwtToken) {
    this(jwtToken, "Bearer");
  }
}
