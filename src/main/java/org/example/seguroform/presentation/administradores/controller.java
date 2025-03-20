package org.example.seguroform.presentation.administradores;

import org.example.seguroform.logic.Administrador;
import org.example.seguroform.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller("administradores")
@RequestMapping("/presentation/administadores")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/about")
    public String about() {
        return "/presentation/aboutUs/aboutUs";
    }

    @GetMapping("/create")
    public String createAdministrador(@ModelAttribute Administrador administrador) {
        service.administradorAdd(administrador);
        return "redirect:/presentation/login/ViewLogin"; // Hay que cambiar las rutas
    }
    @GetMapping("/delete/{id}")
    public String deleteAdministrador(@PathVariable("id") String id) {
        service.administradorDel(id);
        return "redirect:/presentation/login/ViewLogin";
    }
}