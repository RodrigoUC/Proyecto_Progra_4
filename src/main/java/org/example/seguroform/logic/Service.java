package org.example.seguroform.logic;

import org.example.seguroform.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private SlotRepository slotRepository;
    @Autowired
    private CitaRepository citaRepository;

    //Usuarios
    public Iterable<Usuario> usuariosFindAll() {
        return usuarioRepository.findAll();
    }
    public void usuarioAdd(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    public void usuarioDel(String id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario usuarioFindById(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario validarUsuario(String id, String password) {
        // Buscar el usuario por su id
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        // Verificar si el usuario existe y si la contraseña coincide
        if (usuario != null && usuario.getClave().equals(password)) {
            return usuario;
        } else {
            return null; // Retornar null si el usuario no es válido
        }
    }

    //Pacientes
    public Iterable<Paciente> pacienteFindAll(){
        return pacienteRepository.findAll();
    }
    public void pacienteAdd(Paciente paciente) {
        pacienteRepository.save(paciente);
    }
    public void pacienteDel(String id) {
        pacienteRepository.deleteById(id);
    }
    public Paciente pacienteFindById(String id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    // Medicos
    public Iterable<Medico> medicoFindAll(){
        return medicoRepository.findAll();
    }

    public void medicoAdd(Medico m){
        medicoRepository.save(m);
    }

    public void medicoDel(String id){
        medicoRepository.deleteById(id);
    }

    public Medico medicoGet(String id){
        return medicoRepository.findById(id).orElse(null);
    }

    //Administradores
    public Iterable<Administrador> administradorFindAll(){
        return administradorRepository.findAll();
    }
    public void administradorAdd(Administrador administrador) {
        administradorRepository.save(administrador);
    }
    public void administradorDel(String id) {
        administradorRepository.deleteById(id);
    }
    public Administrador administradorFindById(String id) {
        return administradorRepository.findById(id).orElse(null);
    }

    //Slots
    public Iterable<Slot> slotFindAll(){
        return slotRepository.findAll();
    }
    public void slotAdd(Slot slot) {
        slotRepository.save(slot);
    }
    public void slotDel(Integer id) {
        slotRepository.deleteById(id);
    }
    public Slot slotFindById(Integer id) {
        return slotRepository.findById(id).orElse(null);
    }

    //Citas
    public Iterable<Cita> citaFindAll(){
        return citaRepository.findAll();
    }
    public void citaAdd(Cita cita) {
        citaRepository.save(cita);
    }
    public void citaDel(Integer id) {
        citaRepository.deleteById(id);
    }
    public Cita citaFindById(Integer id) {
        return citaRepository.findById(id).orElse(null);
    }

}
