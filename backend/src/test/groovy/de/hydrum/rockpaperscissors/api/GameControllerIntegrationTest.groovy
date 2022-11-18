package de.hydrum.rockpaperscissors.api

import com.fasterxml.jackson.databind.ObjectMapper
import de.hydrum.rockpaperscissors.model.Game
import de.hydrum.rockpaperscissors.shared.GameOption
import de.hydrum.rockpaperscissors.shared.GameOutcome
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.transaction.Transactional
import java.time.OffsetDateTime

import static org.hamcrest.Matchers.greaterThanOrEqualTo
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.notNullValue
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class GameControllerIntegrationTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private EntityManager entityManager

    @Autowired
    private ObjectMapper objectMapper

    def "getResults returns saved info"() {
        given:
        Game game1 = new Game(
            playerName: "test1",
            playerSelection: GameOption.ROCK,
            cpuSelection: GameOption.ROCK,
            outcome: GameOutcome.DRAW,
            time: OffsetDateTime.now()
        )
        entityManager.persist(game1)
        and:
        Game game2 = new Game(
            playerName: "test2",
            playerSelection: GameOption.ROCK,
            cpuSelection: GameOption.ROCK,
            outcome: GameOutcome.DRAW,
            time: OffsetDateTime.now()
        )
        entityManager.persist(game2)
        and:
        entityManager.flush()
        entityManager.clear()

        when:
        MvcResult result = mockMvc
            .perform(
                get("/api/game"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("\$.numberOfElements", greaterThanOrEqualTo(2)))
            .andExpect(jsonPath("\$.content", notNullValue()))
            .andReturn()

        then:
        result
    }

    def "play is successful"() {
        given:
        GameInputDto input = new GameInputDto(name: "test", option: GameOption.PAPER)

        when:
        MvcResult result = mockMvc
            .perform(post("/api/game/play")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(input))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("\$.playerName", is(input.name)))
            .andExpect(jsonPath("\$.playerSelection", is(input.option as String)))
            .andReturn()

        then:
        result
    }

}
