package org.example.seguroform.logic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "medicos")
public class Medico {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Usuario usuarios;

    @Size(max = 50)
    @NotNull
    @Column(name = "especialidad", nullable = false, length = 50)
    private String especialidad;

    @NotNull
    @Column(name = "costo_consulta", nullable = false)
    private Float costoConsulta;

    @Size(max = 100)
    @NotNull
    @Column(name = "localidad", nullable = false, length = 100)
    private String localidad;

    @Size(max = 100)
    @Column(name = "horario_semanal", length = 100)
    private String horarioSemanal;

    @NotNull
    @Column(name = "frecuencia_citas", nullable = false)
    private Integer frecuenciaCitas;

    @Size(max = 255)
    @Column(name = "foto_url")
    private String fotoUrl;

    @Size(max = 500)
    @Column(name = "resena", length = 500)
    private String resena;

    @ColumnDefault("0")
    @Column(name = "aprobado")
    private Boolean aprobado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Float getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(Float costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getHorarioSemanal() {
        return horarioSemanal;
    }

    public void setHorarioSemanal(String horarioSemanal) {
        this.horarioSemanal = horarioSemanal;
    }

    public Integer getFrecuenciaCitas() {
        return frecuenciaCitas;
    }

    public void setFrecuenciaCitas(Integer frecuenciaCitas) {
        this.frecuenciaCitas = frecuenciaCitas;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getResena() {
        return resena;
    }

    public void setResena(String resena) {
        this.resena = resena;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

}