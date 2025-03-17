package org.example.seguroform.presentation.usuarios;

import org.example.seguroform.logic.Service;
import org.example.seguroform.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller("usuarios")
@RequestMapping("/presentation/usuarios")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/newlogin")
    public String newLogin(Model model) {
        //model.addAttribute("usuario", new Usuario());
        return "/presentation/login/ViewLogin";
    }
    @GetMapping("/create")
    public String createUsuario(@ModelAttribute Usuario usuario) {
        service.usuarioAdd(usuario);
        return "redirect:/presentation/login/ViewLogin"; // Hay que cambiar las rutas
    }
    @GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable("id") String id) {
        service.usuarioDel(id);
        return "redirect:/presentation/login/ViewLogin";
    }

}
