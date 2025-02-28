package com.monoSpringAereo.monoSpringAereo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monoSpringAereo.monoSpringAereo.model.Prenotazione;
import com.monoSpringAereo.monoSpringAereo.model.Volo;

import java.util.List;

public interface IPrenotazione extends JpaRepository<Prenotazione, Long> {
	
	// Metodi di default JPA Repository:

	// save(Entity) - salva o aggiorna entità
	// findById(ID) - trova per ID
	// findAll() - trova tutti
	// deleteById(ID) - elimina per ID
	// delete(Entity) - elimina entità
	// count() - conta tutte le entità
	// existsById(ID) - verifica esistenza per ID
	// saveAll(Iterable<Entity>) - salva lista entità
	// findAllById(Iterable<ID>) - trova per lista di ID
	// deleteAll() - elimina tutto
	
    List<Prenotazione> findByVolo(Volo volo);
}
