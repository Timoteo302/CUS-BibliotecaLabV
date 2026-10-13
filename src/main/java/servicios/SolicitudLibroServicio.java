/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import repositorios.SolicitudLibroDAO;
import entidades.SolicitudLibro;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class SolicitudLibroServicio {

    @Inject
    private SolicitudLibroDAO solicitudLibroDAO;

    public void guardar(SolicitudLibro solicitudLibro) {
        solicitudLibroDAO.guardar(solicitudLibro);
    }

    public void eliminar(SolicitudLibro solicitudLibro) {
        solicitudLibroDAO.eliminar(solicitudLibro);
    }

}