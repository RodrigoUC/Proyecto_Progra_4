package org.example.seguroform.presentation.usuarios;

import jakarta.servlet.http.HttpSession;
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

@GetMapping("/register")
    public String register(Model model) {
        return "/presentation/registro/ViewRegistro";
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

    @PostMapping("/login")
    public String login(@ModelAttribute("usuario") Usuario usuario, HttpSession session,Model model) {
        Usuario u = service.usuarioFindById(usuario.getId());
        if(u != null && u.getClave().equals(usuario.getClave())){
            session.setAttribute("usuario", usuario);
        }
        model.addAttribute("Error", "Usuario o clave incorrecta");
        return "/presentation/login/ViewLogin";
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
