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
 * Clase que controla las categorias a las que pertenecen los temas del foro
 *
 * @author Adrian
 * @version final
 * @since 1.8
 */
@Entity
@Table(name = "categorias")
@ManagedBean(name = "categoria", eager = false)
@SessionScoped
public class Categoria implements Serializable {

    @Id
    @JoinColumn(name = "idCategoria")
    private int idCategoria;
    private String nombre;
    private String descripcion;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    /**
     * metodo que reinicia los parametros del bean
     */
    public void limpiarDatos() {
        this.idCategoria = 0;
        this.nombre = null;
        this.descripcion = null;
    }

    /**
     * metodo que muestra la lista de categorias del foro
     *
     * @return lista de categorias
     */
    public ArrayList<Categoria> getCategorias() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Categoria> listaCategorias = (ArrayList<Categoria>) gdao.get("Categoria");
            return listaCategorias;
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return null;
        }
    }

    /**
     * metodo para añadir una nueva categoria
     *
     * @return true si se añade correctamente, false si surge algun error
     */
    public String addCategoria() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.add(this);
            return "true";
        } catch (HibernateException | NullPointerException e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
            limpiarDatos();
            return "false";
        }
    }

    /**
     * metodo para eliminar una categoria
     *
     * @return true si se elimina correctamente, false si surge algun error
     */
    public String deleteCategoria() {
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



}
