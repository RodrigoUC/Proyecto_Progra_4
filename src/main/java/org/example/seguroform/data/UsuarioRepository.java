package org.example.seguroform.data;

import org.example.seguroform.logic.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
}
