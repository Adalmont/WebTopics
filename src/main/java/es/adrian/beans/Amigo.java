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
 * Clase que controla la lista de amigos
 *
 * @author Adrian
 * @version final
 * @since 1.8
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
    private String estado;

    /*NUEVO*/

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Metodo para reiniciar los parametros del bean
     */
    public void limpiarDatos() {
        this.usuario = null;
        this.amigo = null;
        this.estado = null;
    }

    /**
     * Metodo para añadir a un amigo a la lista de amigos
     *
     * @return true si se añade sin errores, false si surge alguno
     */
    public String addAmigo() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "p";
            gdao.add(this);
            limpiarDatos();
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * metodo para confirmar una solicitud de amistas
     *
     * @return true si se produce la operacion sin errores, false si surge
     * alguno
     */
    public String confirmarAmigo() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "a";
            gdao.update(this);
            Amigo aceptante= new Amigo();
            aceptante.usuario = this.amigo;
            aceptante.amigo = this.usuario;
            aceptante.estado = "a";
            gdao.add(aceptante);
            limpiarDatos();
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * metodo para borrar a un amigo de la lista de amigos
     *
     * @return true si se realiza la operacion sin errores, false si surge
     * alguno
     */
    public String deleteAmigo() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.delete(this);
            limpiarDatos();
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * Metodo para conseguir la lista de amigos de un usuario
     *
     * @return lista de amigos
     */
    public ArrayList<Amigo> getAmigos() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Amigo> listaAmigos = (ArrayList<Amigo>) gdao.get("Amigo where idUsuario=" + this.usuario.getIdUsuario());
            return listaAmigos;
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return null;
        }
    }

    /**
     * metodo para conseguir una lista de las solicitudes de amistad pendientes
     * de un usuario
     *
     * @return lista de solicitudes
     */
    public ArrayList<Amigo> getSolicitudes() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Amigo> listaSolicitudes = (ArrayList<Amigo>) gdao.get("Amigo where idAmigo=" + this.usuario.getIdUsuario());
            return listaSolicitudes;
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return null;
        }
    }

}
