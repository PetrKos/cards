package cards.logic.controller;

import cards.logic.dto.JwtAuthenticationResponse;
import cards.logic.dto.LoginRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authorization")
@RequestMapping("/api/login")
public interface AuthorizationAPI {

  @Operation(
          description = """
                  Regarding authentication,
                  you are to generate a JSON Web Token to store user info -
                  said token can be included in the “Authorization“ header (as an OAuth 2.0 Bearer Token) in subsequent
                  API calls to identify the calling user.
                  """,
          summary = """
                  Generates a jwt token to be able to make calls to the api.
                  """,
          responses = {
                  @ApiResponse(description = "OK", responseCode = "200")

                  //add bad request here?
          })
  @PostMapping("/token")
  ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest);
}
