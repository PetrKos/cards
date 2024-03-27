package cards.logic.controller;

import cards.logic.dto.CardDto;
import cards.logic.dto.CardsRequestDto;
import cards.logic.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing card operations.
 * This class handles HTTP requests related to card actions such as retrieving,
 * creating, updating, and deleting cards.
 */
@RestController
public class CardController implements CardAPI {

  private final CardService cardService;

  public CardController(CardService cardService) {
    this.cardService = cardService;
  }

  @Override
  public String homePage() {
    return "Hello and welcome to cards app!";
  }

  /**
   * Retrieves a paginated list of cards based on the provided request parameters.
   *
   * @param cardsRequestDto Data transfer object containing query parameters for filtering and pagination.
   * @return A ResponseEntity containing a page of CardDto objects.
   */
  @Override
  public ResponseEntity<Page<CardDto>> getAllCardsPage(CardsRequestDto cardsRequestDto) {
    Page<CardDto> cards = cardService.findAllCardsPage(cardsRequestDto);
    return ResponseEntity.ok(cards);
  }

  /**
   * Creates a new card with the provided card details.
   *
   * @param cardDto Data transfer object containing the details of the card to be created.
   * @return A ResponseEntity with the created CardDto and HTTP status code 201 (Created).
   */
  @Override
  public ResponseEntity<CardDto> createCard(CardDto cardDto) {
    CardDto createdCardDto = cardService.createCard(cardDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCardDto);
  }

  /**
   * Updates an existing card identified by the cardId with new details provided in the cardDetails DTO.
   *
   * @param cardId      The ID of the card to update.
   * @param cardDetails Data transfer object containing the new details for the card.
   * @return A ResponseEntity containing the updated CardDto or HTTP status code 404 (Not Found) if the card does not exist.
   */
  @Override
  public ResponseEntity<CardDto> updateCard(Long cardId, CardDto cardDetails) {
    return cardService.updateCard(cardId, cardDetails)
            .map(updatedCard -> new ResponseEntity<>(updatedCard, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Deletes an existing card identified by the cardId.
   *
   * @param cardId The ID of the card to delete.
   * @return A ResponseEntity with HTTP status code 204 (No Content) if the delete operation is successful,
   *         or HTTP status code 404 (Not Found) if the card does not exist.
   */
  @Override
  public ResponseEntity<Void> deleteCard(Long cardId) {
    if (cardService.deleteCard(cardId)) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
