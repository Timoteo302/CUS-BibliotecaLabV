/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

/**
 *
 * @author timot
 */

import entidades.SolicitudLibro;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;

@Stateless
public class SolicitudLibroDAO {

    @PersistenceContext
    private EntityManager em;

    public void guardar(SolicitudLibro solicitudLibro) {
        em.persist(solicitudLibro);
    }

    public void eliminar(SolicitudLibro solicitudLibro) {
        em.remove(em.merge(solicitudLibro));
    }

}
