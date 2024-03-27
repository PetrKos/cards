package cards.logic.config;

import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Utility class for generating JSON Web Keys (JWKs) for RSA.
 * This class provides methods to generate RSA keys that can be used
 * for signing and verifying JWT tokens in a Spring Security context.
 */
public class Jwks {

  private Jwks() {}

  /**
   * Generates an RSA key pair and wraps it in an RSAKey object suitable for use with JWT processing.
   * The method uses {@link KeyGeneratorUtils} to generate the key pair,
   * ensuring that the keys are of appropriate strength and format for cryptographic operations.
   *
   * @return RSAKey containing the generated RSA public and private keys, along with a randomly generated key ID.
   */
  public static RSAKey generateRsa() {
    KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
  }
}
