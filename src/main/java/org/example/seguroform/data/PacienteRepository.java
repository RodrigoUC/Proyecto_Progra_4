package org.example.seguroform.data;

import org.example.seguroform.logic.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PacienteRepository extends JpaRepository<Paciente, String> {
}
