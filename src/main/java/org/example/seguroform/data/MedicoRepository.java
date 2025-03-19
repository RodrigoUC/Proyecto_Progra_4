package org.example.seguroform.data;

import org.example.seguroform.logic.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MedicoRepository extends JpaRepository<Medico, String> {
}
