package org.example.seguroform.presentation.citas;

import org.example.seguroform.logic.Cita;
import org.example.seguroform.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("citas")
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

//    @GetMapping("/show2")
//    public String show2(Model model) {
//        model.addAttribute("citas", service.citaFindAll());
//        return "/presentation/medicoGestionCitas/ViewMedicoGestionCitas";
//    }

    @GetMapping("/searchPatName")
    public String searchPatName(@RequestParam("patient") String name, @RequestParam("status") String status, Model model) {
        // Obtener citas filtradas por nombre
        List<Cita> citas = (List<Cita>) service.citasFindByName(name);

        // Si se selecciona "todas", devolver todas las citas del paciente
        if (status.equals("todas")) {
            model.addAttribute("citas", citas);
            return "/presentation/medicoGestionCitas/ViewMedicoGestionCitas";
        }

        // Inicializar lista filtrada
        List<Cita> citas2 = new ArrayList<>();

        // Filtrar citas por estado
        for (Cita cita : citas) {
            if (cita.getEstado().equalsIgnoreCase(status)) {
                citas2.add(cita);
            }
        }

        model.addAttribute("citas", citas2);
        return "/presentation/medicoGestionCitas/ViewMedicoGestionCitas";
    }


    @PostMapping("/attendCita")
    public String actualizarEstadoCita(@RequestParam("idCita") int id, @RequestParam("action") String action) {
        Cita cita = service.citaFindById(id);
        if (cita != null) {
            if ("attend".equals(action)) {
                cita.setEstado("completada");
            } else if ("cancel".equals(action)) {
                cita.setEstado("cancelada");
            }
            service.citaUpdate(cita);
        }
        return "/presentation/medicoGestionCitas/ViewMedicoGestionCitas";
    }

}
