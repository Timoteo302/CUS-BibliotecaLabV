/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import repositorios.SolicitudDAO;
import entidades.Solicitud;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class SolicitudServicio {

    @Inject
    private SolicitudDAO solicitudDAO;

    public void guardar(Solicitud solicitud) {
        solicitudDAO.guardar(solicitud);
    }

    public Solicitud buscarPorId(Integer id) {
        return solicitudDAO.buscarPorId(id);
    }

    public void eliminar(Solicitud solicitud) {
        solicitudDAO.eliminar(solicitud);
    }

    //backend
    public Solicitud buscarPorSocio(Integer socioId) {
        return solicitudDAO.buscarPorSocio(socioId);
    }

}
