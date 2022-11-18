package de.hydrum.rockpaperscissors.api

import de.hydrum.rockpaperscissors.service.GameService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class GameControllerTest extends Specification {

    private GameService service

    private GameController controller

    def setup() {
        service = Mock()

        controller = new GameController(service)
    }

    def "play delegates to the service"() {
        given:
        GameInputDto inputDto = new GameInputDto()

        when:
        controller.play(inputDto)

        then:
        1 * service.play(inputDto)
    }

    def "getResults delegates to the service"() {
        given:
        Pageable pageable = Pageable.unpaged()

        when:
        controller.getResults(pageable)

        then:
        1 * service.getResults(pageable)
    }

    def "getResults returns the results from the service"() {
        given:
        Pageable pageable = Pageable.unpaged()
        Page<GameResultDto> page = Page.empty(pageable)
        service.getResults(pageable) >> page

        when:
        Page<GameResultDto> result = controller.getResults(pageable)

        then:
        result == page
    }

}
