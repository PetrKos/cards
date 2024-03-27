package cards.logic.service;


import cards.logic.dto.CardDto;
import cards.logic.dto.CardsRequestDto;
import cards.logic.dto.mappers.CardMapper;
import cards.logic.model.Card;
import cards.logic.model.User;
import cards.logic.model.enums.Role;
import cards.logic.model.enums.Status;
import cards.logic.model.specs.CardSpecifications;
import cards.logic.repository.CardRepository;
import cards.logic.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the CardService interface, handling business logic for card-related operations.
 * This service manages interactions with the Card and User repositories to perform CRUD operations
 * and queries for Card entities based on various criteria.
 */
@Service
public class CardServiceImpl implements CardService {

  private final CardRepository cardRepository;
  private final UserRepository userRepository;
  private final CardSpecifications cardSpecifications;
  private final TokenService tokenService;

  public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository, CardSpecifications cardSpecifications, TokenService tokenService) {
    this.cardRepository = cardRepository;
    this.userRepository = userRepository;
    this.cardSpecifications = cardSpecifications;
    this.tokenService = tokenService;
  }

  /**
   * Retrieves a paginated list of cards, potentially applying filters and sorting based on the
   * CardsRequestDto. The method adjusts query criteria based on the role of the user making the request.
   *
   * @param cardsRequestDto DTO containing pagination and filtering criteria.
   * @return A page of CardDto objects.
   */
  @Override
  public Page<CardDto> findAllCardsPage(CardsRequestDto cardsRequestDto) {
    String userRole = tokenService.getClaimFromJwtToken("scope");
    Specification<Card> cardSpecification;
    if (userRole.equals(Role.ADMIN.toString())) {
      cardSpecification = cardSpecifications.fromDto(cardsRequestDto);
      Page<Card> cards = cardRepository.findAll(cardSpecification, CardsRequestDto.getPageable(cardsRequestDto));
      return cards.map(CardMapper.INSTANCE::cardToCardDto);
    }
    String userEmail = tokenService.getClaimFromJwtToken("sub");
    Long userId = getUserIdByEmail(userEmail);
    Page<Card> cards = cardRepository.findCardsByUserId(userId, CardsRequestDto.getPageable(cardsRequestDto));
    return cards.map(CardMapper.INSTANCE::cardToCardDto);

  }

  /**
   * Creates a new Card entity from a CardDto and persists it in the database.
   *
   * @param cardDto DTO containing the details of the card to create.
   * @return The created CardDto with ID populated.
   */
  @Override
  public CardDto createCard(CardDto cardDto) {
    Card card = CardMapper.INSTANCE.cardDtoToCard(cardDto);
    setUpCard(card);
    Card savedCard = cardRepository.save(card);
    return CardMapper.INSTANCE.cardToCardDto(savedCard);
  }


  private void setUpCard(Card card) {
    card.setStatus(Status.TO_DO);
    String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
    card.setUserId(getUserIdByEmail(principalName));
  }

  /**
   * Updates an existing card with provided details. This operation is restricted to cards
   * owned by the currently authenticated user. The method looks up the card by ID and user ID,
   * updates its fields with the provided {@link CardDto} details, and saves the changes.
   *
   * @param cardId The ID of the card to update.
   * @param cardDetails DTO containing new details for the card.
   * @return An {@link Optional} containing the updated {@link CardDto}, or an empty {@link Optional} if the card does not exist or does not belong to the user.
   */
  @Override
  public Optional<CardDto> updateCard(Long cardId, CardDto cardDetails) {
    String userEmail = tokenService.getClaimFromJwtToken("sub");
    Long userId = getUserIdByEmail(userEmail);
    List<Card> userCards = cardRepository.findCardsByUserId(userId);
    Optional<Card> card = getMatchingCard(cardId, userCards);
    return card
            .map(existingCard -> {
              CardMapper.INSTANCE.updateCardFromDto(cardDetails, existingCard);
              Card updatedCard = cardRepository.save(existingCard);
              return Optional.of(CardMapper.INSTANCE.cardToCardDto(updatedCard));
            })
            .orElse(Optional.empty());
  }

  private Optional<Card> getMatchingCard(Long cardId, List<Card> userCards) {
    return userCards.stream().filter(it -> Objects.equals(it.getId(), cardId)).findFirst();
  }

  /**
   * Deletes a card identified by its ID, restricting the operation to the cards owned by the currently authenticated user.
   * The method verifies ownership of the card by matching the user ID before proceeding with the delete operation.
   *
   * @param cardId The ID of the card to delete.
   * @return true if the card was successfully deleted; false if the card does not exist or does not belong to the user.
   */
  @Override
  public boolean deleteCard(Long cardId) {
    String userEmail = tokenService.getClaimFromJwtToken("sub");
    Long userId = getUserIdByEmail(userEmail);
    List<Card> userCards = cardRepository.findCardsByUserId(userId);
    Optional<Card> card = getMatchingCard(cardId, userCards);
    return card.map(existingCard -> {
              cardRepository.delete(existingCard);
              return true;
            })
            .orElse(false);
  }

  private Long getUserIdByEmail(String principalName) {
    Optional<User> user = userRepository.findByEmail(principalName);
    return user.map(User::getId)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with email:[%s]", principalName)));
  }

}

