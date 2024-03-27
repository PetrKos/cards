package cards.logic.dto.validation_utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SortByValidator.class)
public @interface ValidSortBy {
  String message() default "Invalid sortBy value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

