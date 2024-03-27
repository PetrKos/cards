package cards.logic.controller;

import cards.logic.dto.CardDto;
import cards.logic.dto.CardsRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Cards")
@RequestMapping("/api/cards")
public interface CardAPI {

  @GetMapping
  String homePage();

  @Operation(
          description = """
                  A user can search through cards they have access to.
                  Filters include name, color, status and date of creation.
                  Optionally limit results using page & size options.
                  Results may be sorted by name, color, status, date of creation
                  """,
          summary = """
                  Retrieves a page of  all cards of the current user. Admins will retrieve a page of all available cards.
                  """,
          responses = {
                  @ApiResponse(description = "OK", responseCode = "200")

                  //add bad request here?
          })
  @PostMapping("/cards-retrieval")
  ResponseEntity<Page<CardDto>> getAllCardsPage(@Valid @RequestBody CardsRequestDto cardsRequestDto);


  @Operation(
          description = """
                  A user creates a card by providing a name for it and, optionally, a description and a color.
                  Name is mandatory.
                  Color, if provided, should conform to a “6 alphanumeric characters prefixed with a #“ format.
                  Upon creation, the status of a card is To Do
                  """,
          summary = """
                  Creates a card.
                  """,
          responses = {
                  @ApiResponse(description = "Created", responseCode = "201")
          })
  @PostMapping("/card-creation")
  ResponseEntity<CardDto> createCard(@Valid @RequestBody CardDto cardDto);

  @Operation(
          description = """
                  A user can update the name, the description, the color and/or the status of a card they have access to.
                  Contents of the description and color fields can be cleared out.
                  Available statuses are To Do, In Progress and Done.
                  """,
          summary = """
                  Updates a card.
                  """,
          responses = {
                  @ApiResponse(description = "Created", responseCode = "201"),
                  @ApiResponse(description = "Not Found", responseCode = "404")
          })
  @PutMapping("/card-modification/{cardId}")
  ResponseEntity<CardDto> updateCard(@PathVariable Long cardId, @Valid @RequestBody CardDto cardDto);



  @Operation(
          description = """
                  A user can delete a card they have access to.
                  """,
          summary = """
                  Deletes a card.
                  """,
          responses = {
                  @ApiResponse(description = "No Content", responseCode = "204"),
                  @ApiResponse(description = "Not Found", responseCode = "404")
          })
  @DeleteMapping("/card-deletion/{cardId}")
  ResponseEntity<Void> deleteCard(@PathVariable Long cardId);

}
