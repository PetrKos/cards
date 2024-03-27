package cards.logic.dto.validation_utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/**
 * Custom validator for sort by parameters in query requests.
 * Ensures the provided sort by value is within a predefined list of allowed fields.
 */
public class SortByValidator implements ConstraintValidator<ValidSortBy, String> {

  private final List<String> allowedFields = Arrays.asList("name", "color", "status", "creationDate");

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null && allowedFields.contains(value);
  }
}