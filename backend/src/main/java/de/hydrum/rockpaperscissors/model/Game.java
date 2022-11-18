package de.hydrum.rockpaperscissors.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.hydrum.rockpaperscissors.shared.GameOption;
import de.hydrum.rockpaperscissors.shared.GameOutcome;
import lombok.Data;

/**
 * Entity that represents a game
 */
@Data
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name", length = 50, nullable = false)
    private String playerName;

    @Column(name = "player_choice", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameOption playerSelection;

    @Column(name = "cpu_choice", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameOption cpuSelection;

    @Column(name = "outcome", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameOutcome outcome;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime time = OffsetDateTime.now();

}
