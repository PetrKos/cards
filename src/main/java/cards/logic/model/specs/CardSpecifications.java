package cards.logic.model.specs;


import cards.logic.dto.CardsRequestDto;
import cards.logic.model.Card;
import static cards.logic.model.utils.CardConstants.COLOR;
import static cards.logic.model.utils.CardConstants.CREATION_DATE;
import static cards.logic.model.utils.CardConstants.NAME;
import static cards.logic.model.utils.CardConstants.PERCENT;
import static cards.logic.model.utils.CardConstants.STATUS;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Component responsible for creating specifications used for querying Card entities based on various criteria.
 * Specifications are used in conjunction with Spring Data JPA to dynamically build queries.
 */
@Component
public class CardSpecifications {

  /**
   * Creates a specification for filtering cards based on attributes provided in a CardsRequestDto.
   *
   * @param cardsRequestDto DTO containing filter criteria.
   * @return A Specification<Card> object used for filtering queries.
   */
  public Specification<Card> fromDto(CardsRequestDto cardsRequestDto) {
    return (Root<Card> cardRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      addValuePredicateIfValid(cardsRequestDto.name(), NAME, predicates, cardRoot, criteriaBuilder, PredicateType.LIKE);
      addValuePredicateIfValid(cardsRequestDto.color(), COLOR, predicates, cardRoot, criteriaBuilder, PredicateType.EQUAL);
      addValuePredicateIfValid(cardsRequestDto.status(), STATUS, predicates, cardRoot, criteriaBuilder, PredicateType.EQUAL);
      addValuePredicateIfValid(cardsRequestDto.creationDate(), CREATION_DATE, predicates, cardRoot, criteriaBuilder, PredicateType.DATE_EQUAL);
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  private void addValuePredicateIfValid(String value, String fieldName, List<Predicate> predicates, Root<Card> cardRoot, CriteriaBuilder criteriaBuilder, PredicateType type) {
    if (StringUtils.isNotBlank(value)) {
      switch (type) {
        case LIKE -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(cardRoot.get(fieldName)), PERCENT + value.toLowerCase() + PERCENT));
        case EQUAL -> predicates.add(criteriaBuilder.equal(cardRoot.get(fieldName), value));
        case DATE_EQUAL -> predicates.add(criteriaBuilder.equal(cardRoot.get(fieldName), LocalDate.parse(value)));
      }
    }
  }

  private enum PredicateType {
    LIKE,
    EQUAL,
    DATE_EQUAL
  }
}
