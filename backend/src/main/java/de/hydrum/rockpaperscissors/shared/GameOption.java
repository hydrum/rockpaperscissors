package de.hydrum.rockpaperscissors.shared;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum class that represents the possible choices
 */
@Schema(enumAsRef = true)
public enum GameOption {
    ROCK,
    PAPER,
    SCISSORS;

    /**
     * Returns the outcome when comparing the current option to a cpu option
     *
     * @param cpuOption the option the cpu has chosen
     * @return the outcome of both game options
     */
    public GameOutcome getOutcomeAgainstCpuChoice(GameOption cpuOption) {
        if (this == cpuOption) {
            return GameOutcome.DRAW;
        }

        return switch (cpuOption) {
            case ROCK -> this == PAPER ? GameOutcome.PLAYER_WIN : GameOutcome.CPU_WIN;
            case PAPER -> this == SCISSORS ? GameOutcome.PLAYER_WIN : GameOutcome.CPU_WIN;
            case SCISSORS -> this == ROCK ? GameOutcome.PLAYER_WIN : GameOutcome.CPU_WIN;
        };
    }
}
