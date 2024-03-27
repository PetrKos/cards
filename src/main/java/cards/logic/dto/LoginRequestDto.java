package cards.logic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * A record representing a login request data transfer object (DTO).
 * This DTO is used for encapsulating user login information, specifically the username
 * (which must be a valid email) and password, ensuring that both are provided and properly formatted.
 *
 * @param username The user's login identifier, which must be a valid email address.
 *                 It is required and must not be blank.
 * @param password The user's password, which is required and must not be blank.
 */
public record LoginRequestDto(
        @NotNull(message = "username cannot be null")
        @NotBlank(message = "username cannot be blank")
        @Email(message = "username must be a valid email")
        String username,
        @NotNull(message = "password cannot be null")
        @NotBlank(message = "password cannot be blank")
        String password) {
}
