package de.hydrum.rockpaperscissors.api;

import java.time.OffsetDateTime;

import de.hydrum.rockpaperscissors.shared.GameOption;
import de.hydrum.rockpaperscissors.shared.GameOutcome;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Dto representation of a game
 */
@Data
@Schema(description = "Representation of a game")
public class GameResultDto {

    @Schema(description = "The player name")
    private String playerName;

    @Schema(description = "The option the player has chosen")
    private GameOption playerSelection;

    @Schema(description = "The option the cpu has chosen")
    private GameOption cpuSelection;

    @Schema(description = "The outcome of the game")
    private GameOutcome outcome;

    @Schema(description = "The time when the game was played")
    private OffsetDateTime time;
}
