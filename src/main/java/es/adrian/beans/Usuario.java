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
 * Clase que controla todo lo relacionado con los usuarios de la aplicacion
 *
 * @author Adrian
 * @version final
 * @since 1.8
 */
@Entity
@Table(name = "usuarios")
@ManagedBean(name = "usuario", eager = false)
@SessionScoped
public class Usuario implements Serializable {

    @Id
    @Column(name = "idUsuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String apodo;
    private String tipo;
    private String clave;
    private String avatar;
    private String fechaAlta;
    @Transient
    private String mensaje;
    @Transient
    private String confirmarClave;
    @Transient
    private Part imgSubir;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public Part getImgSubir() {
        return imgSubir;
    }

    public void setImgSubir(Part imgSubir) {
        this.imgSubir = imgSubir;
    }

    /**
     * Metodo para reiniciar los parametros del bean
     */
    public void limpiarDatos() {
        this.idUsuario = 0;
        this.nombre = null;
        this.apellidos = null;
        this.email = null;
        this.apodo = null;
        this.tipo = null;
        this.clave = null;
        this.avatar = null;
        this.fechaAlta = null;
        this.mensaje = null;
        this.confirmarClave = null;
    }

    /**
     * Metodo que reinicia los datos del usuario y devuelve una cadena para la
     * navegacion de jsf
     *
     * @return true
     */
    public String logOut() {
        limpiarDatos();
        return "true";
    }

    /**
     * metodo para crear un nuevo usuario en la base de datos
     *
     * @return true si se crea correctamente, false si no se ha elegido ciudad,
     * la comprobacion de contraseñas falla o se produce un error
     * @throws Exception
     */
    public String addUsuario() throws Exception{
        if (this.clave.equals(this.confirmarClave)) {
            try {
                DAOFactory daof = DAOFactory.getDAOFactory();
                IGenericoDAO gdao = daof.getGenericoDAO();
                this.tipo = "e";
                this.clave = encriptarMD5(this.clave);
                gdao.add(this);
                logUsuario();
                if (this.imgSubir != null) {
                    subirAvatar();
                }
                return "true";

            } catch (HibernateException | NullPointerException e) {
                limpiarDatos();
                this.mensaje = "Ha habido un error en el registro";
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
                return "false";
            }
        } else {
            limpiarDatos();
            this.mensaje = "Las contraseñas no coinciden";
            return "false";
        }
    }

    /**
     * Metodo para logear a un usuario en la aplicacion
     *
     * @return true si no se produce ningun error, false si los datos no
     * coinciden o se produce un error
     * @throws Exception
     */
    public String logUsuario() throws Exception {
        DAOFactory daof = DAOFactory.getDAOFactory();
        IGenericoDAO gdao = daof.getGenericoDAO();
        ArrayList<Usuario> user;
        if (this.nombre == null && this.apellidos == null) {
            user = (ArrayList<Usuario>) gdao.get("Usuario as u where u.email= '" + this.email + "' and u.clave= '" + encriptarMD5(this.clave) + "'");
        } else {
            user = (ArrayList<Usuario>) gdao.get("Usuario as u where u.email= '" + this.email + "' and u.clave= '" + this.clave + "'");
        }
        if (!user.isEmpty()) {
            if (!user.get(0).getTipo().equals("b")) {
                this.email = user.get(0).email;
                this.apellidos = user.get(0).apellidos;
                this.nombre = user.get(0).nombre;
                this.avatar = user.get(0).avatar;
                this.idUsuario = user.get(0).idUsuario;
                this.tipo = user.get(0).tipo;
                this.clave = user.get(0).clave;
                this.apodo = user.get(0).apodo;
                this.fechaAlta = user.get(0).fechaAlta;
                this.confirmarClave = null;
                this.mensaje = null;
                return "true";
            } else {
                limpiarDatos();
                this.mensaje = "Usuario bloqueado";
                return "false";
            }
        } else {
            limpiarDatos();
            this.mensaje = "Email o contraseña incorrectos";
            return "false";
        }

    }

    /**
     * Metodo para actualizar a un usuario en la base de datos
     *
     * @return true si se actualiza correctamente, false si se produce un error
     * @throws Exception
     */
    public String updateUsuario() throws Exception {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            gdao.update(this);
            logUsuario();
            return "true";

        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * metodo para obtener la lista de usuarios de la aplicacion excepto
     * administradores
     *
     * @return lista de usuarios
     */
    public ArrayList<Usuario> getUsuarios() {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) gdao.get("Usuario where tipo='n' or tipo='b'");
            return listaUsuarios;
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return null;
        }
    }

    /**
     * metodo para bloquear/desbloquear usuarios
     *
     * @param usuario usuario a bloquear/desbloquear
     * @param bloquear operacion a realizar (bloquear/desbloquear)
     * @return true si se realiza la operacion sin errores, false si se produce
     * alguno
     * @throws Exception
     */
    public String bloquearUsuario(Usuario usuario, String bloquear) throws Exception {
        try {
            DAOFactory daof = DAOFactory.getDAOFactory();
            IGenericoDAO gdao = daof.getGenericoDAO();
            if (bloquear.equals("bloquear")) {
                usuario.tipo = "b";
                gdao.update(usuario);
            }
            if (bloquear.equals("desbloquear")) {
                usuario.tipo = "n";
                gdao.update(usuario);
            }
            return "true";
        } catch (HibernateException he) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, he);
            return "false";
        }
    }

    /**
     * Metodo para cambiar el avatar del usuario
     *
     * @throws IOException
     * @throws Exception
     */
    public void subirAvatar() throws IOException, Exception {
        /*idUsuario+numeroAvatar?*/
        setAvatar(subirImagen("avatares", this.imgSubir, String.valueOf(this.idUsuario)));
        updateUsuario();
    }

    /**
     * metodo para subir una imagen al proyecto
     *
     * @param carpeta carpeta a la que se desea subir la imagen
     * @param archivo archivo que se desea guardar
     * @param nombre nombre que se desea poner a la imagen guardada
     * @return nombre de la imagen guardada
     * @throws IOException
     */
    static String subirImagen(String carpeta, Part archivo, String nombre) throws IOException {
        String filename = FilenameUtils.getBaseName(nombre);
        String extension = FilenameUtils.getExtension(".jpg");
        Path fichero = Paths.get("C:\\NetBeansProjects\\WebTopics\\src\\main\\webapp\\resources\\imagenes\\avatares" + System.getProperty("file.separator") + filename + "." + extension);
        Path file = Files.createFile(fichero);
        try (InputStream input = archivo.getInputStream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("Imagen subida a: " + file);

        return file.getFileName().toString();

    }

    /**
     * metodo para encriptar la contraseña del usuario
     *
     * @param cadena contraseña a encriptar
     * @return contraseña encriptada
     * @throws Exception
     */
    public static String encriptarMD5(String cadena) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(cadena.getBytes());
        byte[] digest = md.digest();
        byte[] encoded = Base64.encodeBase64(digest);
        return new String(encoded);
    }
}
