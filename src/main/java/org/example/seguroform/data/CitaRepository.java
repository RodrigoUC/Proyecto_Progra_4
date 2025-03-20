package org.example.seguroform.data;

import org.example.seguroform.logic.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
}
