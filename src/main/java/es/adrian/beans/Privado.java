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
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.HibernateException;

/**
 *
 * @author Adrian
 */
@Entity
@Table(name = "privados")
@ManagedBean(name = "privado", eager = false)
@SessionScoped
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
    private String leido;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Transient
    private int numeroMensajes;

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

    public String getLeido() {
        return leido;
    }

    public void setLeido(String leido) {
        this.leido=leido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

    public int getNumeroMensajes(){
        return numeroMensajes;
    }

    public void setNumeroMensajes(int numeroMensajes){
        this.numeroMensajes=numeroMensajes;
    }
    

    public void limpiarDatos() {
        this.idPrivado=0;
        this.creador=null;
        this.receptor=null;
        this.leido=null;
        this.titulo=null;
        this.contenido=null;
        this.fechaCreacion=null;
    }

    public String updatePrivado(){
        try{                                                                      
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.update(this);
            return "true";

        }catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }

    }

    public String addPrivado(Usuario receptor){
        try{                                                                      
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.leido="n";
            this.receptor=receptor;
            gdao.add(this);
            return "true";

        }catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    public String deletePrivado() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.delete(this);
            return "true";
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return "false";
        }
    }

    public ArrayList<Privado> getPrivados(){
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Privado> listaPrivados = (ArrayList<Privado>) gdao.get("Privado");
            return listaPrivados;
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return null;
        }
    }

    public boolean privadosPendientes(int receptor) {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Privado> listaPrivados = (ArrayList<Privado>) gdao.get("Privado where idReceptor=" + receptor + " and leido='n'");
            if (!listaPrivados.isEmpty()) {
                this.numeroMensajes = listaPrivados.size();
                return true;
            } else {
                return false;
            }
        } catch (HibernateException he) {
            Logger.getLogger(Privado.class.getName()).log(Level.SEVERE, null, he);
            return false;
        }
    }

    public boolean privadosArchivados(int receptor) {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Privado> listaPrivados = (ArrayList<Privado>) gdao.get("Privado where idReceptor=" + receptor + " and leido='l'");
            if (!listaPrivados.isEmpty()) {
                this.numeroMensajes = listaPrivados.size();
                return true;
            } else {
                return false;
            }
        } catch (HibernateException he) {
            Logger.getLogger(Privado.class.getName()).log(Level.SEVERE, null, he);
            return false;
        }
    }

    public String leerMensajes(int receptor){
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Privado> listaNoLeidos = (ArrayList<Privado>) gdao.get("Privado where idReceptor = "+ receptor + " and leido = 'n'");
            for (Privado i : listaNoLeidos){
                i.setLeido("l");
                gdao.update(i);
            }
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Privado.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }
}   