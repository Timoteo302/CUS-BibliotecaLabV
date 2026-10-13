/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Prestamo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.PrestamoDAO;
import entidades.Libro;
import entidades.LibroPrestamo;
import entidades.Socio;
import entidades.Solicitud;
import entidades.SolicitudLibro;
import excepciones.ValidacionException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import repositorios.LibroDAO;
import repositorios.LibroPrestamoDAO;

/**
 *
 * @author timot
 */
@Stateless
public class PrestamoServicio {

    @Inject
    private PrestamoDAO prestamoDAO;

    // ======== BACKEND ============
    @Inject
    private LibroPrestamoServicio libroPrestamoServicio;

    @Inject
    private LibroServicio libroServicio;

    @Inject
    private SolicitudServicio solicitudServicio;

    @Inject
    private SolicitudLibroServicio solicitudLibroServicio;

    @Inject
    private LibroDAO libroDAO;

    @Inject
    private LibroPrestamoDAO libroPrestamoDAO;

    //backend
    public void crearPrestamo(
            Socio socio,
            Solicitud solicitud)
            throws ValidacionException {

        //buscar préstamos activos
        List<Prestamo> activos
                = buscarActivosPorSocio(socio.getId());

        //validación TP punto b
        validarPrestamosVencidos(activos);

        //validación TP punto e
        validarLimiteLibros(
                activos,
                solicitud);

        //fecha actual
        Date hoy = new Date();

        //fecha vencimiento +7 días
        Calendar calendar
                = Calendar.getInstance();

        calendar.setTime(hoy);
        calendar.add(
                Calendar.DAY_OF_MONTH,
                7);

        //crear préstamo
        Prestamo prestamo
                = new Prestamo();

        prestamo.setSocio(socio);
        prestamo.setFecha(hoy);
        prestamo.setFechaVencimiento(
                calendar.getTime());
        //prestamo.setMulta(0f);

        guardar(prestamo);

        //guardar libros del préstamo
        for (SolicitudLibro sl
                : solicitud.getSolicitudLibros()) {

            Libro libro = sl.getLibro();

            LibroPrestamo lp = new LibroPrestamo();

            lp.setPrestamosId(prestamo);
            lp.setLibrosId(libro);
            lp.setDevuelto(false);

            libroPrestamoServicio.guardar(lp);

            //descontar stock
            libro.setDisponibles(
                    libro.getDisponibles() - 1);

            libroServicio.actualizar(libro);

            //eliminar detalle solicitud
            solicitudLibroServicio.eliminar(sl);
        }

        //eliminar cabecera solicitud
        solicitudServicio.eliminar(solicitud);
    }

    // METODOS 
    //
    // Buscar préstamos activos del socio
    public List<Prestamo> buscarActivosPorSocio(Integer socioId) {
        return prestamoDAO.buscarActivosPorSocio(socioId);
    }

    //buscar un prestamo por un DNI del socio:
    public List<LibroPrestamo> buscarPrestamosSocio(Integer dni) {

        return prestamoDAO.buscarPorSocio(dni);

    }

    // METODOS PRIVADOS
    //
    /*1. Validar préstamos vencidos
    Recorre todos los prestamos activos, si encuentra uno vencido
    lanza excepcion.*/
    private void validarPrestamosVencidos(List<Prestamo> prestamosActivos) throws ValidacionException {

        Date hoy = new Date();

        for (Prestamo prestamo : prestamosActivos) {

            if (prestamo.getFechaVencimiento()
                    .before(hoy)) {

                throw new ValidacionException(
                        "El socio posee préstamos vencidos");
            }
        }
    }

    /*2. Si tiene 2 libros prestados y pide 2 mas, da excepcion*/
    private void validarLimiteLibros(
            List<Prestamo> prestamosActivos,
            Solicitud solicitud)
            throws ValidacionException {

        int librosActivos = 0;

        for (Prestamo prestamo : prestamosActivos) {

            librosActivos
                    += prestamo.getLibrosprestamosCollection()
                            .size();
        }

        int librosSolicitados
                = solicitud.getSolicitudLibros().size();

        if (librosActivos + librosSolicitados > 3) {

            throw new ValidacionException(
                    "El socio supera el límite de 3 libros");
        }
    }

    // =============================
    public List<Prestamo> listarTodos() {
        return prestamoDAO.listarTodos();
    }

    public Prestamo buscarPorId(Integer id) {
        return prestamoDAO.buscarPorId(id);
    }

    public void guardar(Prestamo prestamo) {
        prestamoDAO.guardar(prestamo);
    }

    public void actualizar(Prestamo prestamo) {
        prestamoDAO.actualizar(prestamo);
    }

    public void eliminar(Integer id) {
        prestamoDAO.eliminar(id);
    }

    // ===== METODOS PARA EL ADMIN ====
    // backend
    public List<Prestamo> listarActivos() {

        return prestamoDAO.listarActivos();

    }

// backend
    public List<Prestamo> listarFinalizados() {

        return prestamoDAO.listarFinalizados();

    }

    // backend
// devuelve cada libro prestado como fila individual
    public List<LibroPrestamo> listarLibrosActivos() {

        List<LibroPrestamo> resultado = new ArrayList<>();

        for (Prestamo p : prestamoDAO.listarActivos()) {

            for (LibroPrestamo lp : p.getLibrosprestamosCollection()) {

                if (!Boolean.TRUE.equals(lp.getDevuelto())) {

                    resultado.add(lp);

                }

            }

        }

        return resultado;
    }

// backend
// historial de libros devueltos
    public List<LibroPrestamo> listarLibrosFinalizados() {

        List<LibroPrestamo> resultado = new ArrayList<>();

        for (Prestamo p : prestamoDAO.listarTodos()) {

            for (LibroPrestamo lp : p.getLibrosprestamosCollection()) {

                if (Boolean.TRUE.equals(lp.getDevuelto())) {

                    resultado.add(lp);

                }

            }

        }

        return resultado;
    }

    //backend
    public void devolverLibro(LibroPrestamo libroPrestamo) {

        Date hoy = new Date();

        // libro que se devuelve
        Libro libro = libroPrestamo.getLibrosId();

        // suma disponibilidad
        libro.setDisponibles(
                libro.getDisponibles() + 1
        );

        libroDAO.actualizar(libro);

        // marcar ESTE libro como devuelto
        libroPrestamo.setDevuelto(true);
        libroPrestamo.setFechaDevolucion(hoy);

        libroPrestamoDAO.actualizar(libroPrestamo);

        // volvemos a leer el préstamo desde BD
        Prestamo prestamo
                = prestamoDAO.buscarPorId(
                        libroPrestamo.getPrestamosId().getId()
                );

        // calcular multa de ESTE libro
        long diferencia
                = hoy.getTime()
                - prestamo.getFechaVencimiento().getTime();

        long dias
                = diferencia
                / (1000 * 60 * 60 * 24);

        if (dias > 0) {

            libroPrestamo.setMulta(
                    dias * 500f
            );

        } else {

            libroPrestamo.setMulta(0f);

        }

        // revisar si quedan libros pendientes
        boolean todosDevueltos = true;

        for (LibroPrestamo lp
                : prestamo.getLibrosprestamosCollection()) {

            if (!Boolean.TRUE.equals(lp.getDevuelto())) {
                return;

            }

        }

        // cerrar préstamo
        if (todosDevueltos) {

            prestamo.setFechaDevolucion(hoy);

            prestamoDAO.actualizar(prestamo);

        }

    }

    // nuevos metodos
    public List<LibroPrestamo> listarPrestamosVencidos() {

        return prestamoDAO.listarPrestamosVencidos();

    }

    public List<Object[]> librosMasPrestados() {

        return prestamoDAO.librosMasPrestados();

    }

    public List<Object[]> sociosConMasPrestamos() {

        return prestamoDAO.sociosConMasPrestamos();

    }

    // NUEVO METODO DE LISTAR MOROSOS EN /ADMIN/SOCIOS PERO CON PRESTAMOS
    public List<LibroPrestamo> listarMorosos() {

        return prestamoDAO.listarMorososPrestamo();

    }

}
