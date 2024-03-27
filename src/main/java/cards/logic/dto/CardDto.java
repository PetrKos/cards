package cards.logic.dto;

import cards.logic.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a card entity.
 * This record is used to transfer card data between processes,
 * while also enforcing validation constraints for card properties.
 *
 * Includes:
 * - ID of the card (nullable for creation scenarios where the ID is not yet assigned).
 * - Name of the card, which is mandatory and limited to 255 characters.
 * - Optional description of the card, limited to 500 characters.
 * - Optional hexadecimal color code prefixed with "#", exactly 7 characters long including the prefix.
 * - Status of the card represented by the {@link Status} enum.
 * - Creation date of the card represented by {@link LocalDate}, not included if null to avoid serialization of null values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CardDto(
        Long id,
        @NotNull(message = "Name is mandatory")
        @NotBlank(message = "Name is mandatory")
        @Size(max = 255, message = "Name must be at most 255 characters long")
        String name,
        @Size(max = 500, message = "Description must be at most 500 characters long")
        String description,
        @Pattern(regexp = "^$|^#([A-Fa-f0-9]{6})$", message = "Color must be a valid hexadecimal code, consisting of 6 alphanumeric characters prefixed with #")
        String color,
        Status status,
        LocalDate creationDate) {
}
