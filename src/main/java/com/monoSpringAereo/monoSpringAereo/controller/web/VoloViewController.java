package com.monoSpringAereo.monoSpringAereo.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.monoSpringAereo.monoSpringAereo.repository.IVolo;

@Controller
@RequestMapping("/voli")
public class VoloViewController {

	@Autowired
	private IVolo voloRepository;

	@GetMapping
	public String listaVoli(Model model) {
		model.addAttribute("voli", voloRepository.findAll());
		return "voli/lista-voli";
	}

	@GetMapping("/{id}")
	public String dettaglioVolo(@PathVariable Long id, Model model) {
		// IReindirizza con id
		return "redirect:/prenotazioni/nuova/" + id;
	}
	
}