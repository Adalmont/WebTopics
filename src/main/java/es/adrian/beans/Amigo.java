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
@Table(name = "amigos")
@ManagedBean(name = "amigo", eager = false)
public class Amigo implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @Id
    @ManyToOne
    @JoinColumn(name = "idAmigo")
    private Usuario amigo;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getAmigo() {
        return amigo;
    }

    public void setAmigo(Usuario amigo) {
        this.amigo = amigo;
    }
    
}
