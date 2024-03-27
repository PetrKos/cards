package cards.logic.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * Service for generating and handling JWT tokens for authentication purposes.
 * Utilizes Spring Security's JwtEncoder for encoding JWTs with custom claims based on the authenticated user.
 */
@Service
public class TokenService {

    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Generates a JWT token for the given authentication object.
     * The token includes standard claims like issuer, subject, and expiration, as well as custom claims such as scope.
     *
     * @param authentication The authentication object containing the principal and authorities.
     * @return A String representation of the JWT token.
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Extracts a specific claim from the JWT token of the currently authenticated user.
     *
     * @param key The key of the claim to extract.
     * @return The value of the specified claim as a String.
     */
    public String getClaimFromJwtToken(String key) {
        Jwt jwtToken = getJwtToken();
        return jwtToken.getClaimAsString(key);
    }

    private Jwt getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Jwt) authentication.getPrincipal();
    }

}
