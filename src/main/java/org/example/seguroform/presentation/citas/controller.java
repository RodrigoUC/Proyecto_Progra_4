package org.example.seguroform.presentation.citas;

import jakarta.servlet.http.HttpSession;
import org.example.seguroform.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller("citas")
@RequestMapping("/presentation/citas")
public class controller {
    @Autowired
    private Service service;

    @PostMapping("/confirmarCita")
    public String confirmarCita(
            @RequestParam("id_pac") String idPaciente, // Recibir el ID del paciente
            @RequestParam("id_cit") Integer idCita
            , Model model) {
        Paciente pac = service.pacienteFindById(idPaciente);
        Cita cit = service.citaFindById(idCita);

        cit.setPaciente(pac);
        cit.setReservada(true);

        service.citaUpdate(cit); //Se actualiza

        System.out.println("Se reservo exitosamente");
        System.out.println(cit.getPaciente().getUsuarios().getNombre());

        return "redirect:/presentation/medicos/show";
    }

    @GetMapping("/showCitas")
    public String showCitas(Model model) {
        return "/presentation/medicoGestionCitas/ViewMedicoGestionCitas";
    }

    @GetMapping("/showHPC")
    public String showHPC(Model model) {
        return "/presentation/pacienteHistoricoCitas/ViewPacienteHistoricoCitas";
    }

    @GetMapping("/showConfirmar")
    public String confirmarCita(Model model, HttpSession session) {
        // Verificar si ya inicio sesion
        if (session.getAttribute("usuario") == null) {
            return "redirect:/presentation/usuarios/login";
        }

        Usuario usuario = new Usuario();
        usuario.setId("u005");
        usuario.setNombre("Pedro Jimenez");

        Medico medico = new Medico();
        medico.setUsuarios(usuario);
        medico.setFoto("/images/medico.png");
        medico.setHospital("Hospital San Rafael");
        Instant fecha = Instant.now();

        Cita cita = new Cita();
        cita.setId(1);
        cita.setFechaCreacion(fecha);
        cita.setMedico(medico);

        model.addAttribute("cita", cita);

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


    @GetMapping("/searchDrName")
    public String searchDrName(@RequestParam("doctor") String name, @RequestParam("status") String status, Model model) {
        // Obtener citas filtradas por nombre
        List<Cita> citas = (List<Cita>) service.citasFindByDoctor(name);

        // Si se selecciona "todas", devolver todas las citas del paciente
        if (status.equals("todas")) {
            model.addAttribute("citasPHC", citas);
            return "/presentation/pacienteHistoricoCitas/ViewPacienteHistoricoCitas";
        }

        // Inicializar lista filtrada
        List<Cita> citas2 = new ArrayList<>();

        // Filtrar citas por estado
        for (Cita cita : citas) {
            if (cita.getEstado().equalsIgnoreCase(status)) {
                citas2.add(cita);
            }
        }

        model.addAttribute("citasPHC", citas2);
        return "/presentation/pacienteHistoricoCitas/ViewPacienteHistoricoCitas";
    }

}
