/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.adrian.beans;

import es.adrian.dao.IGenericoDAO;
import es.adrian.daofactory.DAOFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;

/**
 * Clase que controla los mensajes en los temas
 *
 * @author Adrian
 * @version final
 * @since 1.8
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
    @ManagedProperty(value = "#{tema}")
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

    /**
     * metodo para reiniciar los parametros del bean
     *
     */
    public void limpiarDatos() {
        this.idMensaje = 0;
        this.tema = null;
        this.usuario = null;
        this.fechaCreacion = null;
        this.contenido = null;
    }

    /**
     * metodo para crear un nuevo mensaje en un tema
     *
     * @param tema tema en el que se crea el mensaje
     * @return true si se crea el mensaje sin incidentes, false si surge un
     * error
     */
    public String addMensaje(Usuario usuario) {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.usuario= usuario;
            gdao.add(this);
            limpiarDatos();
            return "true";
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return "false";
        }
    }

    /**
     * metodo para borrar un mensaje de un tema
     *
     * @param categoria
     * @return true si se realiza la operacion sin ningun problema, false si
     * surge algun error
     */
    public String deleteMensaje(String categoria) {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            if (categoria.equals("m") || categoria.equals("a")) {
                this.contenido = "Este mensaje ha sido eliminado por un moderador";
            } else {
                this.contenido = "Este mensaje ha sido eliminado por el usuario";
            }
            gdao.update(this);
            limpiarDatos();
            return "true";
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return "false";
        }
    }

    /**
     * metodo para obtener la lista de mensajes de un tema
     *
     * @param Idtema tema al que pertenecen los mensajes
     * @return lista de mensajes
     */
    public ArrayList<Mensaje> getMensajes(int Idtema) {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Mensaje> listaMensajes = (ArrayList<Mensaje>) gdao.get("Mensaje where idTema=" + Idtema);
            return listaMensajes;
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return null;
        }
    }

    /**
     * metodo para cambiar el contenido de un mensaje informando de que el usuario o el administrador
     * lo han borrado
     * 
     * @param tipoUsuario tipo del usuario que borra el mensaje
     * @return true si la operacion es exitosa, false si se produce un error
     */
    public String borrarMensaje(String tipoUsuario){
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            if (tipoUsuario.equals("m") || tipoUsuario.equals("a")){
                this.contenido = "Este mensaje ha sido eliminado por un moderador";
            }
            if (tipoUsuario.equals("n")){
                this.contenido = "Este mensaje ha sido eliminado por el usuario";
            }
            gdao.update(this);
            return "true";
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return "false";
        }
    }
}
