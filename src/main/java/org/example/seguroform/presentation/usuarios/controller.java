package org.example.seguroform.presentation.usuarios;

import org.example.seguroform.logic.Service;
import org.example.seguroform.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller("usuarios")
@RequestMapping("/presentation/usuarios")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/login")
    public String login(Model model) {
        return "/presentation/login/ViewLogin";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "/presentation/login/ViewLogin";
    }

    @GetMapping("/show")
    public String show(Model model, @AuthenticationPrincipal(expression = "usuario") Usuario usuario){
        Usuario usr = new Usuario();
        usr.setId(usuario.getId());
        model.addAttribute("usuario", usr);
        model.addAttribute("editing", false);
        return "redirect:/presentation/usuarios/login";
    }

    @GetMapping("/create")
    public String createUsuario(@ModelAttribute Usuario usuario) {
        service.usuarioAdd(usuario);
        return "redirect:/presentation/usuarios/login";
    }
    @GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable("id") String id) {
        service.usuarioDel(id);
        return "redirect:/presentation/usuarios/login";
    }

}
