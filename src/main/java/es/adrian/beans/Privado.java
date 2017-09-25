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
@Table(name = "privados")
@ManagedBean(name = "privado", eager = false)
public class Privado implements Serializable {
    @Id
    @Column(name = "idPrivado")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrivado;
    @ManyToOne
    @JoinColumn(name = "idCreador")
    private Usuario creador;
    @ManyToOne
    @JoinColumn(name = "idReceptor")
    private Usuario receptor;
    private String contenido;
    private String titulo;

    public int getIdPrivado() {
        return idPrivado;
    }

    public void setIdPrivado(int idPrivado) {
        this.idPrivado = idPrivado;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}
