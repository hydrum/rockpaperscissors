package de.hydrum.rockpaperscissors.shared;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The outcome of a game
 */
@Schema(enumAsRef = true)
public enum GameOutcome {
    PLAYER_WIN,
    DRAW,
    CPU_WIN
}
