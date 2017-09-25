/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.*;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "mensajes")
@ManagedBean(name = "mensaje", eager = false)
public class Mensaje implements Serializable {
    @Id
    @Column(name = "idMensaje")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMensaje;
    @ManyToOne
    @JoinColumn(name = "idTema")
    private Tema tema;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    private String fechaCreacion;
    private String contenido;

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
}
