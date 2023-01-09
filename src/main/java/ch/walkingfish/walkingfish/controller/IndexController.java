package ch.walkingfish.walkingfish.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/","/accueil"})
    public String index(Model model) {
        return "index";
    }
}
