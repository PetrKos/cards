package cards.logic.config;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Utility class for generating key pairs for cryptographic operations.
 * This class specifically provides support for generating RSA key pairs.
 */
@Component
final class KeyGeneratorUtils {

  private KeyGeneratorUtils() {}


  /**
   * Generates an RSA {@link KeyPair} with a specified key size.
   * This method encapsulates the key generation process, providing a
   * simple way to generate a new RSA {@link KeyPair} for encryption, decryption,
   * signing, or verification purposes.
   *
   * @return A newly generated RSA {@link KeyPair}.
   * @throws IllegalStateException if the RSA algorithm is not available
   *                               or the key pair generation fails.
   */
  public static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }

}
