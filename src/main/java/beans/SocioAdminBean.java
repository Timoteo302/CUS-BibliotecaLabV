/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Autor;
import entidades.Socio;
import excepciones.ValidacionException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import servicios.SocioServicio;
import entidades.Usuario;
import servicios.UsuarioServicio;

/**
 *
 * @author timot
 */
@Named
@ViewScoped
public class SocioAdminBean implements Serializable {

    @Inject
    private SocioServicio socioServicio; //servicio

    private Socio socio;  // entidad

    //obtenemos todos los socios
    private List<Socio> socios;

    // NUEVO AGREGADO:
    @Inject
    private UsuarioServicio usuarioServicio;

    private List<Usuario> usuarios;

    private Usuario usuarioSeleccionado;

    // nuevo
    private List<Socio> sociosMorosos;

    @PostConstruct
    public void init() {

        socio = new Socio();
        cargar();

        //nuevo
        usuarios = usuarioServicio.listarSinSocio();

    }

    public void cargar() {
        socios = socioServicio.listarTodos();
    }

//    public void guardar() {
//
//        try {
//
//            if (socio.getId() == null) {
//
//                socioServicio.guardar(socio);
//
//            } else {
//
//                socioServicio.actualizar(socio);
//
//            }
//            socio = new Socio();
//            cargar();
//
//            FacesContext.getCurrentInstance()
//                    .addMessage(null,
//                            new FacesMessage(
//                                    "Operación realizada correctamente"));
//
//        } catch (ValidacionException e) {
//
//            FacesContext.getCurrentInstance()
//                    .addMessage(null,
//                            new FacesMessage(
//                                    FacesMessage.SEVERITY_ERROR,
//                                    e.getMessage(),
//                                    null
//                            ));
//
//        }
//
//    }
    public void guardar() {

        try {

            if (socio.getId() == null) {

                socioServicio.guardar(socio);

                if (usuarioSeleccionado != null) {

                    usuarioSeleccionado.setSocio(socio);
                    usuarioServicio.actualizar(usuarioSeleccionado);

                }

            } else {

                socioServicio.actualizar(socio);

            }

            socio = new Socio();
            usuarioSeleccionado = null;
            cargar();

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    "Operación realizada correctamente"));

        } catch (ValidacionException e) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    null));

        }

    }

    public void editar(Socio socio) {
        this.socio = socio;
    }

    public void eliminar(Socio socio) {
        socioServicio.eliminar(socio.getId());
        cargar();
    }

    // nuevo para ver socios morosos
    public void verMorosos() {

        sociosMorosos = socioServicio.listarMorosos();

    }

    // getters
    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
    }

    //nuevo
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<Socio> getSociosMorosos() {
        return sociosMorosos;
    }

    public void setSociosMorosos(List<Socio> sociosMorosos) {
        this.sociosMorosos = sociosMorosos;
    }
    
    

}
