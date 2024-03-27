package cards.logic.dto;

import cards.logic.dto.validation_utils.ValidSortBy;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * A Data Transfer Object (DTO) that encapsulates the request parameters for fetching a page of cards.
 * This DTO includes pagination information and sorting criteria, allowing clients to specify
 * how they want the cards to be ordered and presented in a paged format.
 *
 * @param pageNumber The zero-based page index, must not be negative.
 * @param pageSize The size of the page to be returned, must not be greater than 50.
 * @param sortDirection The direction of the sort (ASC or DESC).
 * @param sortBy The property name to sort by. Valid values are specified by {@link ValidSortBy}.
 * @param name Optional filter by the name of the card.
 * @param color Optional filter by the color of the card.
 * @param status Optional filter by the status of the card.
 * @param creationDate Optional filter by the creation date of the card.
 */
public record CardsRequestDto(
        @Min(value = 0, message = "Page number must be non-negative")
        int pageNumber,
        @Min(value = 1, message = "Page size must be at least 1")
        @Max(value = 50, message = "Page size must not exceed 50")
        Integer pageSize,
        Sort.Direction sortDirection,
        @NotNull(message = "Sort by, cannot be null")
        @NotBlank(message = "Sort by, cannot be blank")
        @ValidSortBy(message = "Can only sort by name, color, status or creationDate")
        String sortBy,
        String name,
        String color,
        String status,
        String creationDate
) {
  public CardsRequestDto {
    pageSize = (pageSize == null) ? 10 : pageSize;
    sortDirection = (sortDirection == null) ? Sort.Direction.ASC : sortDirection;
    sortBy = StringUtils.isBlank(sortBy) ? "id" : sortBy;
  }

  /**
   * Converts this DTO into a {@link Pageable} object that can be used with Spring Data repositories
   * to fetch a paginated result set according to the provided request parameters.
   *
   * @param cardsRequestDto The DTO containing the pagination and sorting parameters.
   * @return A {@link Pageable} instance configured according to this DTO.
   */
  public static Pageable getPageable(CardsRequestDto cardsRequestDto) {
    return PageRequest.of(
            cardsRequestDto.pageNumber(),
            cardsRequestDto.pageSize(),
            cardsRequestDto.sortDirection(),
            cardsRequestDto.sortBy());
  }
}





