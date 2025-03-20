package org.example.seguroform.data;

import org.example.seguroform.logic.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, String> {
        public List<Medico> findByEspecialidadContainingAndLocalidadContaining(String especialidad, String localidad);
}
