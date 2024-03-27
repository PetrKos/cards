package cards.logic.service;

import cards.logic.dto.CardDto;
import cards.logic.dto.CardsRequestDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Service interface for managing card operations.
 * Defines the contract for services responsible for handling CRUD operations and queries
 * for {@link CardDto} objects. This interface facilitates abstraction and decoupling
 * between the service layer and controller layer of the application.
 */
public interface CardService {

  /**
   * Retrieves a paginated list of cards based on criteria specified in {@link CardsRequestDto}.
   * This method allows for filtering, sorting, and pagination of card records.
   *
   * @param cardsRequestDto The data transfer object containing the filtering and pagination criteria.
   * @return A {@link Page} of {@link CardDto} that matches the criteria.
   */
  Page<CardDto> findAllCardsPage(CardsRequestDto cardsRequestDto);

  /**
   * Creates a new card record based on the provided {@link CardDto}.
   *
   * @param cardDto The data transfer object containing the details of the card to be created.
   * @return The {@link CardDto} representing the newly created card record.
   */
  CardDto createCard(CardDto cardDto);

  /**
   * Updates an existing card record identified by {@code cardId} with the details provided in {@code cardDetails}.
   *
   * @param cardId The ID of the card to be updated.
   * @param cardDetails The data transfer object containing the new details for the card.
   * @return An {@link Optional} containing the updated {@link CardDto}, or an empty {@link Optional} if no card with the given ID was found.
   */
  Optional<CardDto> updateCard(Long cardId, CardDto cardDetails);

  /**
   * Deletes the card record identified by {@code cardId}.
   *
   * @param cardId The ID of the card to be deleted.
   * @return {@code true} if the card was successfully deleted, {@code false} if no card with the given ID was found.
   */
  boolean deleteCard(Long cardId);
}
