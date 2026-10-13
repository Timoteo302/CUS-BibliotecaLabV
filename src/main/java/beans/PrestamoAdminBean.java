package beans;

import entidades.LibroPrestamo;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import servicios.PrestamoServicio;

@Named
@ViewScoped
public class PrestamoAdminBean implements Serializable {

    @Inject
    private PrestamoServicio prestamoServicio;

    private List<LibroPrestamo> librosActivos;
    private List<LibroPrestamo> librosFinalizados;

    // para buscar por id socio los prestmos de ese socio
    private Integer dniSocio;
    private List<LibroPrestamo> prestamosBuscados;

    // nuevos:
    private List<LibroPrestamo> prestamosVencidos;
    private List<Object[]> librosPopulares;
    private List<Object[]> rankingSocios;

    // NUEVO PARA LISTAR MOROSOS CON PRESTAMOS
    private List<LibroPrestamo> librosMorososPrestamo;

    @PostConstruct
    public void init() {

        cargar();
        prestamosBuscados = new ArrayList<>();
    }

    public void cargar() {

        librosActivos
                = prestamoServicio.listarLibrosActivos();

        librosFinalizados
                = prestamoServicio.listarLibrosFinalizados();

    }

    public void devolver(LibroPrestamo libroPrestamo) {

        prestamoServicio.devolverLibro(libroPrestamo);

        cargar();

        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(
                                "Libro devuelto correctamente"));

    }

    //buscar por dni los prestamos de un socio:
//    public void buscarPorSocio() {
//        prestamosBuscados = prestamoServicio.buscarPrestamosSocio(idSocio);
//    }
    public void buscarPorSocio() {

        prestamosBuscados
                = prestamoServicio.buscarPrestamosSocio(dniSocio);

        if (prestamosBuscados.isEmpty()) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "No se encontraron préstamos",
                                    "No se encontraron préstamos"));

        }

    }

    // nuevos:
    public void verVencidos() {

        prestamosVencidos = prestamoServicio.listarPrestamosVencidos();

    }

    public void verLibrosMasPrestados() {

        librosPopulares = prestamoServicio.librosMasPrestados();

    }

    public void verRankingSocios() {

        rankingSocios = prestamoServicio.sociosConMasPrestamos();

    }

    //Nuevo listar morosos  con prestamos 
    public void verMorosos() {

        librosMorososPrestamo = prestamoServicio.listarMorosos();

    }

    public long calcularDiasAtraso(LibroPrestamo lp) {

        if (lp.getPrestamosId().getFechaVencimiento() == null) {
            return 0;
        }

        if (lp.getPrestamosId().getFechaDevolucion() != null) {
            return 0;
        }

        java.util.Date hoy = new java.util.Date();

        long diferencia
                = hoy.getTime()
                - lp.getPrestamosId().getFechaVencimiento().getTime();

        long dias = diferencia / (1000 * 60 * 60 * 24);

        return dias;

    }

    // getters y setters
    public List<LibroPrestamo> getLibrosActivos() {
        return librosActivos;
    }

    public List<LibroPrestamo> getLibrosFinalizados() {
        return librosFinalizados;
    }

    //nuevo
    public Integer getDniSocio() {
        return dniSocio;
    }

    public void setDniSocio(Integer dniSocio) {
        this.dniSocio = dniSocio;
    }

    public List<LibroPrestamo> getPrestamosBuscados() {
        return prestamosBuscados;
    }

    public void setPrestamosBuscados(List<LibroPrestamo> prestamosBuscados) {
        this.prestamosBuscados = prestamosBuscados;
    }

    // nuevos:
    public List<LibroPrestamo> getPrestamosVencidos() {
        return prestamosVencidos;
    }

    public void setPrestamosVencidos(List<LibroPrestamo> prestamosVencidos) {
        this.prestamosVencidos = prestamosVencidos;
    }

    public List<Object[]> getLibrosPopulares() {
        return librosPopulares;
    }

    public void setLibrosPopulares(List<Object[]> librosPopulares) {
        this.librosPopulares = librosPopulares;
    }

    public List<Object[]> getRankingSocios() {
        return rankingSocios;
    }

    public void setRankingSocios(List<Object[]> rankingSocios) {
        this.rankingSocios = rankingSocios;
    }

    //
    public List<LibroPrestamo> getLibrosMorososPrestamo() {
        return librosMorososPrestamo;
    }

    public void setLibrosMorososPrestamo(List<LibroPrestamo> librosMorososPrestamo) {
        this.librosMorososPrestamo = librosMorososPrestamo;
    }

}
