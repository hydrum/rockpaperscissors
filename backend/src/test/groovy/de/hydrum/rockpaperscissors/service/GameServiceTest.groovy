package de.hydrum.rockpaperscissors.service

import de.hydrum.rockpaperscissors.api.GameInputDto
import de.hydrum.rockpaperscissors.api.GameResultDto
import de.hydrum.rockpaperscissors.mapper.GameMapper
import de.hydrum.rockpaperscissors.model.Game
import de.hydrum.rockpaperscissors.repository.GameRepository
import de.hydrum.rockpaperscissors.shared.GameOption
import de.hydrum.rockpaperscissors.shared.InvalidInputException
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validator

class GameServiceTest extends Specification {

    private GameRepository repository
    private GameMapper mapper
    private Validator validator
    private Counter gamesPlayedCounter

    private GameService service

    def setup() {
        repository = Mock()
        mapper = Mock()
        validator = Mock()

        gamesPlayedCounter = Mock(Counter)
        MeterRegistry meterRegistry = Mock(MeterRegistry)
        meterRegistry.counter(_ as String) >> gamesPlayedCounter

        service = Spy(new GameService(meterRegistry, validator, mapper, repository))
    }

    def "getResults retrieves results from the repository"() {
        given:
        Pageable pageable = Pageable.unpaged()

        when:
        service.getResults(pageable)

        then:
        1 * repository.findAll(pageable) >> Page.empty()
    }

    def "getResults delegates to the mapper to map the entity"() {
        given:
        Game game = new Game()
        and:
        Pageable pageable = Pageable.unpaged()
        repository.findAll(pageable) >> new PageImpl<Game>([game])

        when:
        service.getResults(pageable)

        then:
        1 * mapper.toGameResult(game)
    }

    def "getResults returns the page"() {
        given:
        Game game = new Game()
        GameResultDto dto = new GameResultDto()
        and:
        Pageable pageable = Pageable.unpaged()
        repository.findAll(pageable) >> new PageImpl<Game>([game])
        mapper.toGameResult(game) >> dto

        when:
        Page<GameResultDto> result = service.getResults(pageable)

        then:
        result != null
        result.content.size() == 1
        result.content.every { it == dto }
    }

    def "play throws an exception if the input is invalid"() {
        given:
        GameInputDto input = new GameInputDto()

        when:
        service.play(input)

        then:
        1 * validator.validate(input) >> [Mock(ConstraintViolation)]
        thrown(InvalidInputException)
    }

    def "play creates the game via mapper"() {
        given:
        GameInputDto input = new GameInputDto()
        and:
        Game game = new Game(playerSelection: GameOption.ROCK)
        repository.save(game) >> game
        validator.validate(input) >> []

        when:
        service.play(input)

        then:
        1 * mapper.create(input) >> game
    }

    def "play assigns the choice of the cpu"() {
        given:
        GameInputDto input = new GameInputDto()
        and:
        Game game = new Game(playerSelection: GameOption.ROCK)
        mapper.create(input) >> game
        repository.save(game) >> game
        validator.validate(input) >> []

        when:
        service.play(input)

        then:
        game.getCpuSelection() != null
    }

    def "play saves the game result"() {
        given:
        GameInputDto input = new GameInputDto()
        and:
        Game game = new Game(playerSelection: GameOption.ROCK)
        mapper.create(input) >> game
        validator.validate(input) >> []

        when:
        service.play(input)

        then:
        1 * repository.save(game) >> game
    }

    def "play increases counter"() {
        given:
        GameInputDto input = new GameInputDto()
        and:
        Game game = new Game(playerSelection: GameOption.ROCK)
        mapper.create(input) >> game
        validator.validate(input) >> []
        repository.save(game) >> game

        when:
        service.play(input)

        then:
        1 * gamesPlayedCounter.increment()
    }

    def "play maps the result to the dto and returns it"() {
        given:
        GameInputDto input = new GameInputDto()
        and:
        Game game = new Game(playerSelection: GameOption.ROCK)
        GameResultDto dto = new GameResultDto()
        mapper.create(input) >> game
        repository.save(game) >> game
        validator.validate(input) >> []

        when:
        GameResultDto result = service.play(input)

        then:
        result == dto
        1 * mapper.toGameResult(game) >> dto
    }
}
