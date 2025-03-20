package org.example.seguroform.presentation.citas;

import org.example.seguroform.logic.Cita;
import org.example.seguroform.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller("citas")
@RequestMapping("/presentation/citas")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/showCitas")
    public String showCitas(Model model) {
        return "/presentation/medicoGestionCitas/ViewMedicoGestionCitas";
    }

    @GetMapping("/confirmarCita")
    public String confirmarCita(Model model) {
        return "/presentation/confirmarCita/ViewConfirmarCita";
    }

    @GetMapping("/create")
    public String createCita(@ModelAttribute Cita cita) {
        service.citaAdd(cita);
        return "redirect:/presentation/login/ViewLogin"; // Hay que cambiar las rutas
    }
    @GetMapping("/delete/{id}")
    public String deleteMedico(@PathVariable("id") Integer id) {
        service.citaDel(id);
        return "redirect:/presentation/login/ViewLogin";
    }
}
