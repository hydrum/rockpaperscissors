package de.hydrum.rockpaperscissors.service;

import java.util.Random;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import de.hydrum.rockpaperscissors.shared.InvalidInputException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.hydrum.rockpaperscissors.api.GameInputDto;
import de.hydrum.rockpaperscissors.api.GameResultDto;
import de.hydrum.rockpaperscissors.mapper.GameMapper;
import de.hydrum.rockpaperscissors.model.Game;
import de.hydrum.rockpaperscissors.repository.GameRepository;
import de.hydrum.rockpaperscissors.shared.GameOption;
import de.hydrum.rockpaperscissors.shared.GameOutcome;
import lombok.extern.slf4j.Slf4j;

/**
 * Service to handle games
 */
@Slf4j
@Service
@Transactional
public class GameService {

    private final Counter gamesPlayedCounter;
    private final Validator validator;
    private final GameMapper gameMapper;
    private final GameRepository gameRepository;

    private final Random random;

    /**
     * Constructor with dependency injection
     *
     * @param meterRegistry  The MeterRegistry
     * @param validator      The Validator bean
     * @param gameMapper     The GameMapper
     * @param gameRepository The GameRepository
     */
    @Autowired
    public GameService(MeterRegistry meterRegistry,
                       Validator validator,
                       GameMapper gameMapper,
                       GameRepository gameRepository) {
        this.validator = validator;
        this.gameMapper = gameMapper;
        this.gameRepository = gameRepository;

        this.gamesPlayedCounter = meterRegistry.counter("game.played");

        random = new Random();
    }

    /**
     * Retrieves a page of game results
     *
     * @return Page of game results
     */
    public Page<GameResultDto> getResults(Pageable pageable) {
        return gameRepository.findAll(pageable)
            .map(gameMapper::toGameResult);
    }

    /**
     * Creates and calculates a game based on the user input
     *
     * @param input the user input
     * @return the result as dto representation
     */
    public GameResultDto play(GameInputDto input) {

        // validate input
        Set<ConstraintViolation<GameInputDto>> validationErrors = validator.validate(input);
        if (validationErrors.size() > 0) {
            throw new InvalidInputException();
        }

        // create and set game options
        Game game = gameMapper.create(input);
        game.setCpuSelection(getRandomizedChoice());
        game.setOutcome(calcOutcome(game));
        game = gameRepository.save(game);

        log.info("Result of game {} ({}) vs CPU ({}): {}",
            game.getPlayerName(),
            game.getPlayerSelection(),
            game.getCpuSelection(),
            game.getOutcome());

        gamesPlayedCounter.increment();

        return gameMapper.toGameResult(game);
    }

    private GameOption getRandomizedChoice() {
        int choice = random.nextInt(GameOption.values().length);
        return GameOption.values()[choice];
    }

    private GameOutcome calcOutcome(Game game) {
        return game.getPlayerSelection().getOutcomeAgainstCpuChoice(game.getCpuSelection());
    }
}
