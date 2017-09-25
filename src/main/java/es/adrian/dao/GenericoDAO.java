package es.adrian.dao;

import es.adrian.persistencia.HibernateUtil;
import java.io.Serializable;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.TransactionException;


public class GenericoDAO<T> implements IGenericoDAO<T> {
    
    private Session sesion;
    
    private void iniciaSesion(){
        sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.getTransaction().begin();
    }
    
    private void cierraSesion(){
        try{
        sesion.getTransaction().commit();
        sesion.close();
        }catch(TransactionException e){
            Logger.getLogger(GenericoDAO.class.getName()).log(Level.ALL, null, e);
        }
    }
    
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        sesion.getTransaction().rollback();
        Logger.getLogger(GenericoDAO.class.getName()).log(Level.SEVERE, null, he);
        throw he;
    } 

    @Override
    public void add(T objeto) {
        try {
            iniciaSesion();
            sesion.save(objeto);
            sesion.flush();
            
        } catch (HibernateException he){
            manejaExcepcion(he);
            Logger.getLogger(GenericoDAO.class.getName()).log(Level.SEVERE, null, he);
        } finally {
            cierraSesion();
        }
    }

    @Override
    public <T> List<T> get(String entidad) {
        List<T> listadoResultados = null;
        try {
            iniciaSesion();
            listadoResultados = sesion.createQuery(" from " + entidad).list();
        } catch(HibernateException he){
            this.manejaExcepcion(he);
        } finally {
            this.cierraSesion();
        }
        return listadoResultados;
    }

    @Override
    public <T> T getOne(Serializable pk, Class<T> claseEntidad) {
        T objetoRecuperado = null;
        
        try {
            iniciaSesion();
            objetoRecuperado = sesion.get(claseEntidad, pk);
        } catch(HibernateException he){
            this.manejaExcepcion(he);
        } finally {
            this.cierraSesion();
        }
        
        return objetoRecuperado;
    }
    
    @Override
    public void update(T objeto) {
        try {
            iniciaSesion();
            sesion.update(objeto);
            sesion.flush();
            
        } catch (HibernateException he){
            manejaExcepcion(he);
            Logger.getLogger(GenericoDAO.class.getName()).log(Level.SEVERE, null, he);
        } finally {
            cierraSesion();
        }
    }

    @Override
    public void delete(T objeto) {
        try {
            iniciaSesion();
            sesion.delete(objeto);
        } catch(HibernateException he){
            this.manejaExcepcion(he);
        } finally {
            this.cierraSesion();
        }
    }
}
