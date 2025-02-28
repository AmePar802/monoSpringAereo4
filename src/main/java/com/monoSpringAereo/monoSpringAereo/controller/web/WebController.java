package com.monoSpringAereo.monoSpringAereo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.monoSpringAereo.monoSpringAereo.model.Volo;
import com.monoSpringAereo.monoSpringAereo.repository.IVolo;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

// Gestisce solo la home page e in futuro altre pagine principali ma di minore importanza
@Controller
public class WebController {

    @Autowired
    private IVolo voloRepository;

    @GetMapping({"/", "/home", "/index"})
    public String index(Model model) {
        List<Volo> voli = voloRepository.findAll();
        if(voli.size() > 3) {
            voli = voli.subList(0, 3);
        }
        model.addAttribute("ultimiVoli", voli);
        return "index";
    }

}