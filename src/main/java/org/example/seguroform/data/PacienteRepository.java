package org.example.seguroform.data;

import org.example.seguroform.logic.Paciente;
import org.springframework.data.repository.CrudRepository;

public interface PacienteRepository extends CrudRepository<Paciente, String> {
}
