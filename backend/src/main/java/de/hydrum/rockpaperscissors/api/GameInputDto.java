package de.hydrum.rockpaperscissors.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import de.hydrum.rockpaperscissors.shared.GameOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * The game input dto to represent the input of the player
 */
@Data
@Schema(description = "Representation of the input given by a player")
public class GameInputDto {

    @Schema(description = "The name of the player", required = true)
    @Length(max = 50)
    @NotBlank
    private String name;

    @Schema(description = "The option the player has chosen", required = true)
    @NotNull
    private GameOption option;
}
