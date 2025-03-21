package org.example.seguroform.presentation.administradores;

import org.example.seguroform.logic.Administrador;
import org.example.seguroform.logic.Medico;
import org.example.seguroform.logic.Service;
import org.example.seguroform.logic.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller("administradores")
@RequestMapping("/presentation/administradores")
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

    @GetMapping("/showDoctors")
    public String showDoctors(Model model) {
        Iterable<Medico> med = service.medicoFindAll();
        System.out.println("Hola");
        Iterable<Slot> slot = service.slotFindAll();
        this.setSlots(slot, med);
        for(Medico medico : med) {
            List<Slot> sl = medico.getSlots();
            System.out.println(medico.getUsuarios().getNombre());
            for(Slot s: sl){
                System.out.println(s.getHoraInicio());
            }
        }

        model.addAttribute("citas", service.citaFindAll());
        model.addAttribute("medicos", med);
        return "/presentation/admin/showDoctores";
    }

    public void setSlots(Iterable<Slot> slots, Iterable<Medico> medicos){
        for(Medico medico : medicos) {
            List<Slot> s = new ArrayList<>();
            for (Slot slot : slots) {
                if (medico.getId().equals(slot.getMedico().getId())) {
                    s.add(slot);
                }
            }
            medico.setSlots(s);
        }
    }
}