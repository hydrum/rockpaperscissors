package de.hydrum.rockpaperscissors.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.hydrum.rockpaperscissors.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The game controller
 */
@RestController
@RequestMapping(path = "/api/game")
@Tag(name = "Game", description = "Endpoints to access game data")
public class GameController {

    private final GameService gameService;

    /**
     * Constructor with dependency injection
     *
     * @param gameService The GameService
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Endpoint to start a game based on the provided user input
     *
     * @param gameInput The game input
     * @return the result as dto
     */
    @PostMapping(value = "play", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Play a round of the game")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "The game result of the successfully played game")
    @ApiResponse(responseCode = "500", description = "An internal error has occurred")
    public GameResultDto play(@RequestBody GameInputDto gameInput) {
        return gameService.play(gameInput);
    }

    /**
     * Endpoint to retrieve paged game results
     *
     * @param pageable the pageable
     * @return the page of game results
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve game results based on the provided page and size")
    @ApiResponse(responseCode = "200", description = "All game results")
    @ApiResponse(responseCode = "500", description = "An internal error has occurred")
    public Page<GameResultDto> getResults(@PageableDefault(size = 20, sort = "time", direction = Direction.DESC) Pageable pageable) {
        return gameService.getResults(pageable);
    }

}
