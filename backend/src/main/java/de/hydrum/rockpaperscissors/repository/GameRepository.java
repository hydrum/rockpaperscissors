package de.hydrum.rockpaperscissors.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.hydrum.rockpaperscissors.model.Game;

/**
 * Repository to handle {@link Game} entity
 */
public interface GameRepository extends PagingAndSortingRepository<Game, Long> {
}
