package org.example.seguroform.presentation.medicos;

import org.example.seguroform.logic.Medico;
import org.example.seguroform.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller("medicos")
@RequestMapping("/presentation/medicos")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/create")
    public String createMedico(@ModelAttribute Medico medico) {
        service.medicoAdd(medico);
        return "redirect:/presentation/login/ViewLogin"; // Hay que cambiar las rutas
    }
    @GetMapping("/delete/{id}")
    public String deleteMedico(@PathVariable("id") String id) {
        service.medicoDel(id);
        return "redirect:/presentation/login/ViewLogin";
    }

    @GetMapping("/show")
    public String show(Model model) {
        model.addAttribute("medicos", service.medicoFindAll());
        return "/presentation/buscarCita/ViewbuscarCita";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable("id") String id, Model model) {
        model.addAttribute("medico", service.medicoGet(id));
        return "/presentation/confirmarCita/ViewconfirmarCita";
    }
}