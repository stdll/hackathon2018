package de.msg.gbg.hackathon18.navigalypse.gui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MapController {

    @GetMapping
    public String showMap(Model model) {
        model.addAttribute("hello", "Hello world!???");

        return "map";
    }
}
