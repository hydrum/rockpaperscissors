package de.hydrum.rockpaperscissors.mapper

import de.hydrum.rockpaperscissors.api.GameInputDto
import de.hydrum.rockpaperscissors.api.GameResultDto
import de.hydrum.rockpaperscissors.model.Game
import de.hydrum.rockpaperscissors.shared.GameOption
import de.hydrum.rockpaperscissors.shared.GameOutcome
import spock.lang.Specification

import java.time.OffsetDateTime

class GameMapperTest extends Specification {

    private GameMapper mapper

    def setup() {
        mapper = new GameMapperImpl()
    }

    def "create maps the entity correctly"() {
        given:
        GameInputDto input = new GameInputDto(name: "test321", option: GameOption.SCISSORS)

        when:
        Game result = mapper.create(input)

        then:
        result != null
        result.id == null
        result.playerName == input.name
        result.playerSelection == input.option
        result.cpuSelection == null
        result.outcome == null
        result.time != null
    }

    def "toGameResult maps properties correctly"() {
        given:
        OffsetDateTime now = OffsetDateTime.now()
        Game game = new Game(
            id: 1L,
            playerName: "test213",
            playerSelection: GameOption.PAPER,
            cpuSelection: GameOption.SCISSORS,
            outcome: GameOutcome.DRAW,
            time: now
        )

        when:
        GameResultDto result = mapper.toGameResult(game)

        then:
        result != null
        result.playerName == game.playerName
        result.playerSelection == game.playerSelection
        result.cpuSelection == game.cpuSelection
        result.outcome == game.outcome
        result.time == now
    }
}
