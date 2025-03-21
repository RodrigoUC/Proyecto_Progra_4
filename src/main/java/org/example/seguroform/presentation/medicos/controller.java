package org.example.seguroform.presentation.medicos;

import jakarta.servlet.http.HttpSession;
import org.example.seguroform.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller("medicos")
@RequestMapping("/presentation/medicos")
public class controller {
    @Autowired
    private Service service;

    @ModelAttribute("medicosSearch")
    public Medico medicosSearch() {
        Medico medicosSearch = new Medico();
        medicosSearch.setEspecialidad("");
        medicosSearch.setLocalidad("");
        return medicosSearch;
    }

    @ModelAttribute("medicoSearch")
    public Medico medicoSearch() {
        Medico medicoSearch = new Medico();
        medicoSearch.setId("");
        return medicoSearch;
    }

    @PostMapping("/showExtendido")
    public String showExtendido(@ModelAttribute("medicoSearch") Medico medicoSearch, Model model) {

        Medico med = service.medicoGet(medicoSearch.getId());
        Iterable<Slot> slot = service.slotFindAll();

        List<Slot> arr = new ArrayList<>();
        for (Slot sl : slot) {
            if (med.getId().equals(sl.getMedico().getId())) {
                arr.add(sl);
            }
        }
        med.setSlots(arr);


        model.addAttribute("citas", service.citaFindAll());
        model.addAttribute("medico", med);
        return "/presentation/buscarCita/ViewBuscarHorarioExtendido";
    }

    @GetMapping("/show")
    public String show(Model model) {
        Iterable<Medico> med = service.medicoFindAll();
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
        return "/presentation/buscarCita/ViewBuscarCita";
    }

    @PostMapping("/search")
    public String search( @ModelAttribute("medicosSearch") Medico medicosSearch,Model model) {

        Iterable<Medico> med = service.medicosSearch(medicosSearch.getEspecialidad(), medicosSearch.getLocalidad());
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
        return "/presentation/buscarCita/ViewBuscarCita";
    }

    //Ver como implementar esta parte
    @GetMapping("/showFilter")
    public String showFilter(@ModelAttribute("medicosSearch") Medico medicosSearch, Model model) {
        model.addAttribute("medicos", service.medicosSearch(medicosSearch.getEspecialidad(), medicosSearch.getLocalidad()));
        return "/presentation/buscarCita/ViewBuscarCita";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable("id") String id, Model model) {
        model.addAttribute("medico", service.medicoGet(id));
        return "/presentation/confirmarCita/ViewConfirmarCita";
    }

//     @GetMapping("/create")
//    public String createMedico(@ModelAttribute Medico medico) {
//        service.medicoAdd(medico);
//        return "redirect:/presentation/login/ViewLogin"; // Hay que cambiar las rutas
//    }
//    @GetMapping("/delete/{id}")
//    public String deleteMedico(@PathVariable("id") String id) {
//        service.medicoDel(id);
//        return "redirect:/presentation/login/ViewLogin";
//    }

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