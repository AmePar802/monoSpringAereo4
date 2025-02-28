package com.monoSpringAereo.monoSpringAereo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.monoSpringAereo.monoSpringAereo.model.Prenotazione;
import com.monoSpringAereo.monoSpringAereo.model.Volo;
import com.monoSpringAereo.monoSpringAereo.repository.IPrenotazione;
import com.monoSpringAereo.monoSpringAereo.repository.IVolo;

import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private IPrenotazione prenotazioneRepository;
    
    @Autowired
    private IVolo voloRepository;


    // Metodo interno per salvare una prenotazione, utilizzabile sia dall'API che dal ViewController
    public Prenotazione salvaPrenotazioneInternal(Prenotazione prenotazione) {
        // Gestiamo l'upsert
        if (prenotazione.getId() != null) {
            Prenotazione existing = prenotazioneRepository.findById(prenotazione.getId()).orElse(null);
            if (existing != null && existing.getVolo() != null) {
                // Manteniamo il riferimento al volo originale se non specificato
                if (prenotazione.getVolo() == null) {
                    prenotazione.setVolo(existing.getVolo());
                }
            }
        }
        
        // Ricarichiamo il volo dal DB per evitare entità disconnesse
        if (prenotazione.getVolo() != null && prenotazione.getVolo().getId() != null) {
            Volo volo = voloRepository.findById(prenotazione.getVolo().getId()).orElse(null);
            if (volo != null) {
                prenotazione.setVolo(volo);
                prenotazione.setCodiceVolo(volo.getCodiceVolo());
                
                // Calcola il prezzo totale in base al numero di passeggeri
                int totalePersone = prenotazione.getNAdulti() + prenotazione.getNBambini();
                prenotazione.setPrezzoBiglietto(volo.getPrezzoBase() * totalePersone);
            }
        }
        
        return prenotazioneRepository.save(prenotazione);
    }
    

    // Metodo interno per eliminare una prenotazione
    public void eliminaPrenotazioneInternal(Long id) {
        prenotazioneRepository.deleteById(id);
    }


    // API REST per creare o aggiornare una prenotazione
    @PostMapping
    public ResponseEntity<?> upsertPrenotazione(@RequestBody Prenotazione prenotazione) {
        // Validazione
        if (prenotazione.getVolo() == null) {
            return ResponseEntity.badRequest().body("Volo obbligatorio");
        }
        if (prenotazione.getNAdulti() == null || prenotazione.getNAdulti() < 1) {
            return ResponseEntity.badRequest().body("Almeno un adulto richiesto");
        }
        if (prenotazione.getNBambini() == null || prenotazione.getNBambini() < 0) {
            return ResponseEntity.badRequest().body("Numero bambini non valido");
        }
        
        try {
            Prenotazione saved = salvaPrenotazioneInternal(prenotazione);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore nel salvare la prenotazione: " + e.getMessage());
        }
    }


    // Trova Prenotazione per ID
    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable Long id) {
        return prenotazioneRepository.findById(id).orElse(null);
    }


    // Lista tutte le prenotazioni
    @GetMapping
    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();
    }

    // Elimina Prenotazione
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrenotazione(@PathVariable Long id) {
        if (!prenotazioneRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        eliminaPrenotazioneInternal(id);
        return ResponseEntity.ok().build();
    }
    
    // Trova prenotazioni per un volo specifico
    @GetMapping("/volo/{voloId}")
    public List<Prenotazione> getPrenotazioniByVolo(@PathVariable Long voloId) {
        Volo volo = voloRepository.findById(voloId).orElse(null);
        if (volo != null) {
            return prenotazioneRepository.findByVolo(volo);
        }
        return List.of();
    }
}