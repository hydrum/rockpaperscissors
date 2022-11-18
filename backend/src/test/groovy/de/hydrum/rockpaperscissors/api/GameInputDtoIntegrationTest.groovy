package de.hydrum.rockpaperscissors.api

import de.hydrum.rockpaperscissors.shared.GameOption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.Validator

@SpringBootTest
class GameInputDtoIntegrationTest extends Specification {

    @Autowired
    private Validator validator

    @Unroll
    def "test input is invalid for name #name, option: #option"() {
        given:
        GameInputDto input = new GameInputDto(name: name, option: option)

        when:
        Set<ConstraintViolation> result = validator.validate(input)

        then:
        noExceptionThrown()
        result.size() > 0

        where:
        name           | option              || _
        null           | null                || _
        null           | GameOption.ROCK     || _
        null           | GameOption.PAPER    || _
        null           | GameOption.SCISSORS || _
        ""             | null                || _
        ""             | GameOption.ROCK     || _
        ""             | GameOption.PAPER    || _
        ""             | GameOption.SCISSORS || _
        " "            | null                || _
        " "            | GameOption.ROCK     || _
        " "            | GameOption.PAPER    || _
        " "            | GameOption.SCISSORS || _
        "a".repeat(51) | null                || _
        "a".repeat(51) | GameOption.ROCK     || _
        "a".repeat(51) | GameOption.PAPER    || _
        "a".repeat(51) | GameOption.SCISSORS || _
    }

    @Unroll
    def "test input is valid for #name, option: #option"() {
        given:
        GameInputDto input = new GameInputDto(name: name, option: option)

        when:
        Set<ConstraintViolation> result = validator.validate(input)

        then:
        noExceptionThrown()
        result.size() == 0

        where:
        name           | option              || _
        "a"            | GameOption.ROCK     || _
        "a"            | GameOption.PAPER    || _
        "a"            | GameOption.SCISSORS || _
        "a".repeat(50) | GameOption.ROCK     || _
        "a".repeat(50) | GameOption.PAPER    || _
        "a".repeat(50) | GameOption.SCISSORS || _
    }

}
