package com.example.bloomfilterdemo.controller;

import com.example.bloomfilterdemo.service.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling web UI interactions with the Bloom Filter
 */
@Controller
@RequestMapping("/bloom-filter")
public class WebController {

    @Autowired
    private BloomFilterService bloomFilterService;

    /**
     * Display the main page
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping
    public String index(Model model) {
        return "index";
    }

    /**
     * Check if a name might exist in the Bloom Filter and add it if it doesn't
     * @param name The name to check or add
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @PostMapping("/check-name")
    public String checkOrAddName(@RequestParam String name, Model model) {
        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("message", "Name cannot be empty");
            return "index";
        }

        boolean exists = bloomFilterService.checkOrAddName(name);
        model.addAttribute("message", exists ? "Name might already exist." : "Name added.");
        model.addAttribute("exists", exists);
        return "index";
    }

    /**
     * Clear the Bloom Filter
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @PostMapping("/clear")
    public String clear(Model model) {
        bloomFilterService.clear();
        model.addAttribute("cleared", true);
        return "index";
    }
}