/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author timot
 */
import entidades.Socio;
import excepciones.ValidacionException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import entidades.Solicitud;
import entidades.SolicitudLibro;
import java.util.ArrayList;
import java.util.List;
import servicios.SolicitudServicio;

import java.io.Serializable;
import servicios.PrestamoServicio;

import servicios.SocioServicio;

@Named
@ViewScoped
public class MostradorBean implements Serializable {

    private Integer dniBusqueda;

    private Socio socioEncontrado;

    @Inject
    private SocioServicio socioServicio;

    //agregamos
    @Inject
    private PrestamoServicio prestamoServicio;

    // ===== BACKEND =====
    @Inject
    private SolicitudServicio solicitudServicio;

    private Solicitud solicitudEncontrada;
    private List<SolicitudLibro> librosSolicitados = new ArrayList<>();

    // ===================
    public void buscarSocio() {

        try {

            socioEncontrado = socioServicio.buscarPorDNI(dniBusqueda);

            solicitudEncontrada = solicitudServicio.buscarPorSocio(
                    socioEncontrado.getId());

            if (solicitudEncontrada == null) {

                socioEncontrado = null;
                librosSolicitados.clear();

                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                new FacesMessage(
                                        FacesMessage.SEVERITY_WARN,
                                        "El socio no tiene solicitudes pendientes",
                                        null));

                return;
            }

            librosSolicitados = new ArrayList<>(
                    solicitudEncontrada.getSolicitudLibros());

            
            // borrar igual es sout nomas
            System.out.println("====== DEBUG SOLICITUD ======");
            System.out.println("Solicitud ID: " + solicitudEncontrada.getId());

            System.out.println("Cantidad libros solicitud: "
                    + solicitudEncontrada.getSolicitudLibros().size());

            System.out.println("Cantidad libros array: "
                    + librosSolicitados.size());

            for (SolicitudLibro sl : librosSolicitados) {

                System.out.println(
                        "SOLICITUD LIBRO ID: " + sl.getId()
                );

                System.out.println(
                        "LIBRO: " + sl.getLibro()
                );

                if (sl.getLibro() != null) {
                    System.out.println(
                            "TITULO: " + sl.getLibro().getTitulo()
                    );
                } else {
                    System.out.println("LIBRO ES NULL");
                }
            }

            System.out.println("===========================");
            // borrar igual es sout nomas.

        } catch (ValidacionException e) {

            socioEncontrado = null;
            solicitudEncontrada = null;

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    null));

        }
    }

    // ========= BACKEND ====================
    public void confirmarPrestamo() {

        if (socioEncontrado == null) {
            return;
        }

        if (solicitudEncontrada == null) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "No existen solicitudes pendientes",
                                    null));

            return;
        }

        try {

            prestamoServicio.crearPrestamo(
                    socioEncontrado,
                    solicitudEncontrada);

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Préstamo registrado correctamente",
                                    null));

            solicitudEncontrada = null;
            librosSolicitados.clear();

        } catch (ValidacionException e) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    null));
        }

        // limpiar pantalla
        socioEncontrado = null;
        solicitudEncontrada = null;
        dniBusqueda = null;
        librosSolicitados.clear();
    }
    // ===========================000

    public Integer getDniBusqueda() {
        return dniBusqueda;
    }

    public void setDniBusqueda(Integer dniBusqueda) {
        this.dniBusqueda = dniBusqueda;
    }

    public Socio getSocioEncontrado() {
        return socioEncontrado;
    }

    public void setSocioEncontrado(Socio socioEncontrado) {
        this.socioEncontrado = socioEncontrado;
    }

    public Solicitud getSolicitudEncontrada() {
        return solicitudEncontrada;
    }

    public void setSolicitudEncontrada(Solicitud solicitudEncontrada) {
        this.solicitudEncontrada = solicitudEncontrada;
    }

    public List<SolicitudLibro> getLibrosSolicitados() {
        return librosSolicitados;
    }

    public void setLibrosSolicitados(List<SolicitudLibro> librosSolicitados) {
        this.librosSolicitados = librosSolicitados;
    }

}
