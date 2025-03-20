package org.example.seguroform.presentation.medicos;

import org.example.seguroform.logic.Cita;
import org.example.seguroform.logic.Medico;
import org.example.seguroform.logic.Service;
import org.example.seguroform.logic.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/showExtendido")
    public String showExtendido(Model model) {
        model.addAttribute("medicos", service.medicoFindAll());
        return "/presentation/buscarCita/ViewBuscarHorarioExtendido";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(@PathVariable("id") String id, Model model) {
        model.addAttribute("medico", service.medicoGet(id));
        return "/presentation/confirmarCita/ViewConfirmarCita";
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

    public List<Cita> fechaCitas(LocalDate date){
        List<Cita> citas = new ArrayList<>();
        int dia = date.getDayOfWeek().getValue();
        LocalDateTime t;
        LocalDateTime et;
        for(Slot s : service.slotFindAll()){
            if(s.getDia() == dia){
                t = date.atTime(s.getHoraInicio().getHour(),0);
                et = date.atTime(s.getHoraFin().getHour(),0);
                while(t.isBefore(et)){
                    citas.add(new Cita());
                    t = t.plusMinutes(60);

                }
            }
        }
        return citas;
    }

    public List<LocalTime> horarios(Iterable<Medico> medicos, Iterable<Slot> slots){
        List<LocalTime> horas = new java.util.ArrayList<>(List.of());
        Integer frec;
        LocalTime inic;
        LocalTime fin;
        for(Medico medico : medicos){
            for(Slot slot : slots){
                if(medico.getId().equals(slot.getMedico().getId())){
                    frec = medico.getFrecuenciaCitas();
                    inic = slot.getHoraInicio();
                    fin = slot.getHoraFin();

                    while(inic.isBefore(fin)) {
                        horas.add(inic);
                        inic = inic.plusMinutes(frec);
                    }
                    return horas;
                }
            }
        }
        return horas;
    }

}