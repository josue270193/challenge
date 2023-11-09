package app.josue.challengecalculator.application.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.RedirectView;

@Hidden
@Controller
public class RedirectController {

    @GetMapping("/")
    public RedirectView redirect() {
        return new RedirectView("/swagger-ui.html");
    }

}
