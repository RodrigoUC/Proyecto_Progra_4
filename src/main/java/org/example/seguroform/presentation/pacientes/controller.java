package org.example.seguroform.presentation.pacientes;

import org.example.seguroform.logic.Service;
import org.example.seguroform.logic.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller("pacientes")
@RequestMapping("/presentation/pacientes")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/create")
    public String createPaciente(@ModelAttribute Paciente paciente) {
        service.pacienteAdd(paciente);
        return "redirect:/presentation/login/ViewLogin"; // Hay que cambiar las rutas
    }
    @GetMapping("/delete/{id}")
    public String deletePaciente(@PathVariable("id") String id) {
        service.pacienteDel(id);
        return "redirect:/presentation/login/ViewLogin";
    }
}
