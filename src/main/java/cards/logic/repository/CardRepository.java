package cards.logic.repository;

import cards.logic.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository interface for {@link Card} entities.
 * It extends {@link JpaRepository} for CRUD operations and {@link JpaSpecificationExecutor}
 * for enabling dynamic query generation based on various conditions.
 *
 * This repository provides methods to perform operations on the Card entity, including
 * standard CRUD operations and custom queries to find cards by user ID.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

  /**
   * Retrieves a page of {@link Card} entities belonging to a specific user.
   *
   * @param userId The ID of the user whose cards are to be retrieved.
   * @param pageable A {@link Pageable} object containing pagination and sorting information.
   * @return A {@link Page} of Card entities associated with the specified user ID.
   */
  Page<Card> findCardsByUserId(Long userId, Pageable pageable);

  /**
   * Retrieves a list of all {@link Card} entities belonging to a specific user.
   *
   * @param userId The ID of the user whose cards are to be retrieved.
   * @return A {@link List} of Card entities associated with the specified user ID.
   */
  List<Card> findCardsByUserId(Long userId);
}
