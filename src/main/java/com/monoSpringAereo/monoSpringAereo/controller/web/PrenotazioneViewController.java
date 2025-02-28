package com.monoSpringAereo.monoSpringAereo.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.monoSpringAereo.monoSpringAereo.controller.api.PrenotazioneController;
import com.monoSpringAereo.monoSpringAereo.model.Prenotazione;
import com.monoSpringAereo.monoSpringAereo.model.Volo;
import com.monoSpringAereo.monoSpringAereo.repository.IVolo;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/prenotazioni")
public class PrenotazioneViewController {

    @Autowired
    private PrenotazioneController prenotazioneController;
    
    @Autowired
    private IVolo voloRepository;

    // Lista tutte le prenotazioni
    @GetMapping
    public String listaPrenotazioni(Model model) {
        model.addAttribute("prenotazioni", prenotazioneController.getAllPrenotazioni());
        return "prenotazioni/lista-prenotazioni";
    }
    
    // Form nuova prenotazione
    @GetMapping("/nuova/{voloId}")
    public String nuovaPrenotazione(@PathVariable Long voloId, Model model) {
        Volo volo = voloRepository.findById(voloId).orElse(null);
        if (volo == null) {
            return "redirect:/voli";
        }
        
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setVolo(volo);
        prenotazione.setCodiceVolo(volo.getCodiceVolo());
        prenotazione.setNAdulti(1);
        prenotazione.setNBambini(0);
        prenotazione.setPrezzoBiglietto(volo.getPrezzoBase());
        
        model.addAttribute("prenotazione", prenotazione);
        model.addAttribute("volo", volo);
        
        return "prenotazioni/form";
    }
    
    // Salva prenotazione (api)
    @PostMapping("/salva")
    public String salvaPrenotazione(@ModelAttribute Prenotazione prenotazione) {
        // Usa il controler API invece di direttamente il repository
        prenotazioneController.salvaPrenotazioneInternal(prenotazione);
        return "redirect:/prenotazioni";
    }
    
    // Elimina prenotazione (api)
    @GetMapping("/elimina/{id}")
    public String eliminaPrenotazione(@PathVariable Long id) {
        prenotazioneController.eliminaPrenotazioneInternal(id);
        return "redirect:/prenotazioni";
    }
}