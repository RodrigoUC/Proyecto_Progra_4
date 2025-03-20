package org.example.seguroform.data;

import org.example.seguroform.logic.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, String> {
}
