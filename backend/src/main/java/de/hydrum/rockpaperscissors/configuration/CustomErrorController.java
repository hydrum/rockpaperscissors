package de.hydrum.rockpaperscissors.configuration;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The game controller
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Redirects the user to the index page in case of an error
     *
     * @return resource to forward to
     */
    @RequestMapping("/error")
    public String error() {
        return "forward:/index.html";
    }
}
