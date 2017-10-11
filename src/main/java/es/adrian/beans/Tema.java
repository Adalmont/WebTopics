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
 * Clase que controla los temas de los foros
 *
 * @author Adrian
 * @version final
 * @since 1.8
 */
@Entity
@Table(name = "temas")
@ManagedBean(name = "tema", eager = false)
public class Tema implements Serializable {

    @Id
    @Column(name = "idTema")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTema;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    private String titulo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    private String mensajeInicial;
    private String estado;
    @Transient
    private String mensaje;
    @Transient
    private Categoria catElegida;

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaAlta) {
        this.fechaCreacion = fechaAlta;
    }

    public String getMensajeInicial() {
        return mensajeInicial;
    }

    public void setMensajeInicial(String mensajeInicial) {
        this.mensajeInicial = mensajeInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Categoria getCatElegida() {
        return catElegida;
    }

    public void setCatElegida(Categoria catElegida) {
        this.catElegida = catElegida;
    }

    
    /**
     * metodo para reiniciar los datos del bean
     */
    public void limpiarDatos() {
        this.idTema = 0;
        this.usuario = null;
        this.categoria = null;
        this.titulo = null;
        this.fechaCreacion = null;
        this.mensajeInicial = null;
    }

    /**
     * metodo para añadir un nuevo tema a la base de datos
     *
     * @return true si se lleva a cabo la operacion sin ningun error, false si
     * se produce alguno
     */
    public String addTema() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.add(this);
            return "true";
        } catch (HibernateException | NullPointerException e) {
            limpiarDatos();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            return "false";
        }
    }

    /**
     * metodo para obtener una lista de temas segun la condicion que se le pase
     *
     * @return lista de temas
     */
   

    public String deleteTema() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.delete(this);
            return "true";
        } catch (HibernateException | NullPointerException e) {
            limpiarDatos();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            return "false";
        }
    }

    public String cerrarTema() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            this.estado = "c";
            gdao.update(this);
            return "true";
        } catch (HibernateException | NullPointerException e) {
            limpiarDatos();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            return "false";
        }
    }
    
    
    public String elegirCat(Categoria categoriaElegida) {
        if (categoriaElegida != null) {
            this.catElegida = categoriaElegida;
            return "true";
        } else {
            return "false";
        }
    }
    
    public ArrayList<Tema> getTemas() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Tema> listaTemas = null;
            System.out.print ("ESTACATEGORIA: "+this.catElegida.getIdCategoria());
            if (this.catElegida.getIdCategoria()!=0){
                listaTemas = (ArrayList<Tema>) gdao.get("Tema where idCategoria = "+this.catElegida.getIdCategoria());
            }
            return listaTemas;
        } catch (HibernateException | NullPointerException e) {
            limpiarDatos();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
}
