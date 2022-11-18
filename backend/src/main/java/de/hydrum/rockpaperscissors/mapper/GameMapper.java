package de.hydrum.rockpaperscissors.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.hydrum.rockpaperscissors.api.GameInputDto;
import de.hydrum.rockpaperscissors.api.GameResultDto;
import de.hydrum.rockpaperscissors.model.Game;

/**
 * Mapper that is responsible to map between {@link Game} entity and its dto representation
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GameMapper {

    /**
     * creates a new {@link Game} entity based on the {@link GameInputDto}
     *
     * @param input the input dto
     * @return the new entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "playerName", source = "name")
    @Mapping(target = "playerSelection", source = "option")
    @Mapping(target = "cpuSelection", ignore = true)
    @Mapping(target = "outcome", ignore = true)
    @Mapping(target = "time", ignore = true)
    Game create(GameInputDto input);

    /**
     * converts a {@link Game} entity to a {@link GameResultDto}
     *
     * @param entity the entity to convert
     * @return dto representation
     */
    GameResultDto toGameResult(Game entity);

}
