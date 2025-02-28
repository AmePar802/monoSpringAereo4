package com.monoSpringAereo.monoSpringAereo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.monoSpringAereo.monoSpringAereo.model.Volo;
import com.monoSpringAereo.monoSpringAereo.repository.IVolo;

import java.util.List;

@RestController
@RequestMapping("/api/voli")
public class VoloController {

	@Autowired
	private IVolo voloRepository;

	// Upsert
	@PostMapping
	public ResponseEntity<?> upsertVolo(@RequestBody Volo volo) {
		// Validazione dati
		if (volo.getCodiceVolo() == null || volo.getCodiceVolo().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Codice volo obbligatorio");
		}
		if (volo.getDataPartenza() == null || volo.getDataArrivo() == null) {
			return ResponseEntity.badRequest().body("Date partenza e arrivo obbligatorie");
		}
		if (volo.getDataArrivo().before(volo.getDataPartenza())) {
			return ResponseEntity.badRequest().body("Data arrivo non può essere precedente alla partenza");
		}
		if (volo.getPostiDisponibili() == null || volo.getPostiDisponibili() < 0) {
			return ResponseEntity.badRequest().body("Numero posti non valido");
		}
		if (volo.getPrezzoBase() == null || volo.getPrezzoBase() <= 0) {
			return ResponseEntity.badRequest().body("Prezzo base non valido");
		}

		// Logica upsert
		Volo voloEsistente = voloRepository.findByCodiceVolo(volo.getCodiceVolo());
		if (voloEsistente != null) {
			volo.setId(voloEsistente.getId());
		}
		return ResponseEntity.ok(voloRepository.save(volo));
	}

	// Trova volo per ID
	@GetMapping("/{id}")
	public Volo getVolo(@PathVariable Long id) {
		return voloRepository.findById(id).orElse(null);
	}

	// Lista tutti i voli
	@GetMapping
	public List<Volo> getAllVoli() {
		return voloRepository.findAll();
	}

	// Trova volo per codice
	@GetMapping("/codice/{codiceVolo}")
	public Volo getVoloByCodice(@PathVariable String codiceVolo) {
		return voloRepository.findByCodiceVolo(codiceVolo);
	}

	// Elimina volo
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVolo(@PathVariable Long id) {
		if (!voloRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		voloRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}