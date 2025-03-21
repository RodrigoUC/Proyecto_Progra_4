package org.example.seguroform.presentation.usuarios;

import jakarta.servlet.http.HttpSession;
import org.example.seguroform.logic.Service;
import org.example.seguroform.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

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
    public String login(@ModelAttribute("usuario") Usuario usuario, HttpSession session) {
        Usuario u = service.usuarioFindById(usuario.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(u != null && passwordEncoder.matches(usuario.getClave(), u.getClave())){
            //Medico activo, return que salga: usuario aun no activo
            session.setAttribute("usuario", u);
            if(u.getRol().equals("paciente"))
                return "redirect:/presentation/medicos/show";
            else if(u.getRol().equals("medico"))
                return "redirect:/presentation/citas/showCitas";
            else
                return "redirect:/presentation/administradores/showDoctors";
        }
        else {
            return "redirect:/presentation/usuarios/loginWrong";
        }
    }
    @PostMapping("/userRegister")
    public String userRegister(@ModelAttribute("usuario") Usuario usuario) {
        Usuario u = service.usuarioFindById(usuario.getId());
        if(u == null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
            service.usuarioAdd(usuario);
            usuario.setFechaRegistro(Instant.now());
            if(usuario.getRol().equals("paciente"))
                return "redirect:/presentation/usuarios/login";
            else
                return "redirect:/presentation/citas/showCitas";
        }
        else{
            return "redirect:/presentation/usuarios/userExists";
        }

    }

    @GetMapping("loginWrong")
    public String loginWrong(Model model){
        model.addAttribute("error", "Credenciales Incorrectas");
        return "/presentation/login/ViewLogin";
    }

    @GetMapping("userExists")
    public String userExists(Model model){
        model.addAttribute("error", "error");
        return "/presentation/registro/ViewRegistro";
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

    @GetMapping("/logOut")
    public String logOut(HttpSession session) {
        session.invalidate();
        return "redirect:/presentation/usuarios/login";
    }

}
