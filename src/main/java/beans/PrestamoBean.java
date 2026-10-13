/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author timot
 */
import entidades.Libro;
import entidades.LibroPrestamo;
import entidades.Prestamo;
import entidades.Solicitud;
import entidades.SolicitudLibro;
import entidades.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import servicios.PrestamoServicio;
import servicios.SolicitudLibroServicio;
import servicios.SolicitudServicio;

@Named
@SessionScoped // el carrito tiene que sobrevivir mientras navegamos
public class PrestamoBean implements Serializable {

    private List<Libro> carrito; //para guardar los libros alli
    private boolean carritoVisible;

    @Inject
    private UsuarioBean usuarioBean;

    //para los q no son socios
    private boolean mostrarRegistroSocio;

    // ==== BACKEND ====
    @Inject
    private SolicitudServicio solicitudServicio;

    @Inject
    private SolicitudLibroServicio solicitudLibroServicio;


    // ===================
    @PostConstruct
    public void init() {
        carrito = new ArrayList<>();  // inicializamos la lista
    }

    // logica de carrito libro
    public void agregarLibro(Libro libro) {

        if (carrito.size() < 3) {
            /*Aqui llega el libro, y preguntamos si la lista carrito
        NO contiene el carrito, si es True o sea que no lo tiene
        lo agrega, sino no lo agrega.*/
            if (!carrito.contains(libro)) {
                carrito.add(libro);

                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                new FacesMessage("Libro agregado al préstamo"));

            } else {

                FacesContext.getCurrentInstance()
                        .addMessage(null,
                                new FacesMessage("Ese libro ya está en el préstamo"));
            }
        } else {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage("Máximo permitido: 3 libros"));

        }

    }

    public void quitarLibro(Libro libro) {
        carrito.remove(libro);
    }

    public void limpiar() {
        carrito.clear();
    }

    //vista
    public void mostrarCarrito() {
        //carritoVisible = !carritoVisible;
        //carritoVisible = true;
        if (carritoVisible) {
            carritoVisible = false;
        } else {
            carritoVisible = true;
        }
    }

    //cerrar el carrito
    public void cerrarCarrito() {
        carritoVisible = false;
    }

    //esta un libro en el carrito?
    public boolean estaEnCarrito(Libro libro) {
        return carrito.contains(libro);
    }

    // MEDIO BACKEND
    // GUARDAMOS LA SOLICITUD y los LIBROS de un USUARIO
    public String solicitarPrestamo() {

        Usuario usuario = usuarioBean.getUsuarioLogueado();

        if (usuario == null) {
            return null;
        }

        if (usuario.getSocio() == null) {

            mostrarRegistroSocio = true;
            return null;
        }

        mostrarRegistroSocio = false;

        // si es que ya hay una solicitud.
        Solicitud solicitudExistente
                = solicitudServicio.buscarPorSocio(
                        usuario.getSocio().getId());

        if (solicitudExistente != null) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Ya posee una solicitud pendiente",
                                    null));

            return null;
        }

        //si no hay una solicitud se la crea
        Solicitud solicitud = new Solicitud();

        solicitud.setFecha(new Date());
        solicitud.setSocio(usuario.getSocio());

        solicitudServicio.guardar(solicitud);

        //backend
        for (Libro libro : carrito) {

            SolicitudLibro solicitudLibro = new SolicitudLibro();

            solicitudLibro.setSolicitud(solicitud);
            solicitudLibro.setLibro(libro);

            solicitudLibroServicio.guardar(solicitudLibro);
        }

        carrito.clear();

        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Solicitud enviada correctamente",
                                null));

        return null;
    }



    private Usuario obtenerUsuarioLogueado() {
        return usuarioBean.getUsuarioLogueado();
    }

    // getters y setters
    public List<Libro> getCarrito() {
        return carrito;
    }

    public int getCantidad() {
        return carrito.size();
    }

    public boolean isCarritoVisible() {
        return carritoVisible;
    }

    public void setCarritoVisible(boolean carritoVisible) {
        this.carritoVisible = carritoVisible;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public boolean isMostrarRegistroSocio() {
        return mostrarRegistroSocio;
    }

    public void setMostrarRegistroSocio(boolean mostrarRegistroSocio) {
        this.mostrarRegistroSocio = mostrarRegistroSocio;
    }

    

}
