package de.hydrum.rockpaperscissors.shared

import spock.lang.Specification
import spock.lang.Unroll

class GameOptionTest extends Specification {

    @Unroll
    def "ensure option #playerOption and #cpuOption result in #expectedOutcome"() {
        when:
        GameOutcome outcome = playerOption.getOutcomeAgainstCpuChoice(cpuOption)

        then:
        outcome == expectedOutcome

        where:
        playerOption        | cpuOption           || expectedOutcome
        GameOption.ROCK     | GameOption.ROCK     || GameOutcome.DRAW
        GameOption.ROCK     | GameOption.PAPER    || GameOutcome.CPU_WIN
        GameOption.ROCK     | GameOption.SCISSORS || GameOutcome.PLAYER_WIN
        GameOption.PAPER    | GameOption.ROCK     || GameOutcome.PLAYER_WIN
        GameOption.PAPER    | GameOption.PAPER    || GameOutcome.DRAW
        GameOption.PAPER    | GameOption.SCISSORS || GameOutcome.CPU_WIN
        GameOption.SCISSORS | GameOption.ROCK     || GameOutcome.CPU_WIN
        GameOption.SCISSORS | GameOption.PAPER    || GameOutcome.PLAYER_WIN
        GameOption.SCISSORS | GameOption.SCISSORS || GameOutcome.DRAW
    }
}
