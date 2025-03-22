package org.example.seguroform.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "medicos")
public class Medico {
    @Id
    @Size(max = 40)
    @Column(name = "id", nullable = false, length = 40)
    private String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id", nullable = false)
    private Usuario usuarios;

    @Size(max = 50)
    @NotNull
    @Column(name = "especialidad", nullable = false, length = 50)
    private String especialidad;

    @Size(max = 75)
    @NotNull
    @Column(name = "localidad", nullable = false, length = 75)
    private String localidad;

    @Size(max = 100)
    @NotNull
    @Column(name = "hospital", nullable = false, length = 100)
    private String hospital;

    @NotNull
    @Column(name = "costo_consulta", nullable = false)
    private Float costoConsulta;

    @NotNull
    @Column(name = "frecuencia_citas", nullable = false)
    private Integer frecuenciaCitas;

    @Size(max = 255)
    @NotNull
    @Column(name = "foto", nullable = false)
    private String foto;

    @Lob
    @Column(name = "presentacion")
    private String presentacion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Slot> slots; // ✅ Relación con la tabla "slots"

    @Column(name = "activo")
    private Boolean activo;

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public Medico(){
        this.slots = new ArrayList<>();
        this.id = "";
        this.usuarios = new Usuario();
        this.especialidad = "";
        this.localidad = "";
        this.hospital = "";
        this.costoConsulta = 0.0F;
        this.frecuenciaCitas = 0;
        this.foto = "";
        this.presentacion = "";
        this.activo=false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public Integer getCostoConsulta() {
        return costoConsulta.intValue();
    }

    public void setCostoConsulta(Float costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    public Integer getFrecuenciaCitas() {
        return frecuenciaCitas;
    }

    public void setFrecuenciaCitas(Integer frecuenciaCitas) {
        this.frecuenciaCitas = frecuenciaCitas;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getFecha(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy"));
    }

    public void mostrarDias(){
        for(int i = 1; i<=7; i++){

        }
    }

    /*
    El slot hay que hacerle un each para que muestre las fechas
    Dentro de cada slot se debe hacer un each para las citas
     */

    public List<LocalDate> diasDeTrabajo(){
        Set<LocalDate> diasUnicos = new HashSet<>(); // Usamos Set para evitar duplicados
        LocalDate hoy = LocalDate.now();

        for (Slot s : this.slots) {
            int diaSemana = s.getDia(); // Día de la semana (1 = Lunes, ..., 7 = Domingo)

            // Calculamos la próxima fecha correspondiente a ese día de la semana
            LocalDate fecha = hoy.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.of(diaSemana)));
            diasUnicos.add(fecha); // Agregar la fecha al conjunto
        }

        // Convertimos a lista si es necesario
        List<LocalDate> local = new ArrayList<>(diasUnicos);
        Collections.sort(local);

        return local;
    }

    public List<Cita> fechaCitas(LocalDate actual){
        List<LocalDate> dias = diasDeTrabajo();
        List<Cita> citas = new ArrayList<>();

        for (LocalDate date : dias) { // Iterar sobre cada día de trabajo
            for (Slot s : this.slots) { // Iterar sobre cada slot
                if (s.getDia() == date.getDayOfWeek().getValue()
                        && Objects.equals(this.id, s.getMedico().getId())
                        && actual.isEqual(date)) {
                    // Crear LocalDateTime usando el LocalDate actual y la hora del slot
                    LocalDateTime t = date.atTime(s.getHoraInicio().getHour(), 0);
                    LocalDateTime et = date.atTime(s.getHoraFin().getHour(), 0);

                    // Generar citas en el rango de tiempo
                    while (t.isBefore(et)) {
                        // Convertir LocalDateTime a Instant
                        Instant fechaCreacion = t.atZone(ZoneId.systemDefault()).toInstant();

                        // Crear la cita con el nuevo parámetro
                        citas.add(new Cita(this, s, "pendiente", fechaCreacion));

                        t = t.plusMinutes(frecuenciaCitas);
                    }
                }
            }
        }

        return citas;
    }
}