/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Consulta;
import entidades.Socio;
import entidades.Usuario;
import excepciones.ValidacionException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import servicios.SocioServicio;
import servicios.UsuarioServicio;

/**
 *
 * @author timot
 */
@Named
@ViewScoped
public class SocioBean implements Serializable {

    @Inject
    private SocioServicio socioServicio;

    private Socio socio;
    
    //inyectamos UsuarioBean y UsuarioServicio para los socios y guardarlos
    //relacionar un socio con un usuario.
    @Inject
    private UsuarioServicio usuarioServicio;

    @Inject
    private UsuarioBean usuarioBean;
    
    
    

    @PostConstruct
    public void init() {
        socio = new Socio();
    }

    public void registrar() {
        try {

            socioServicio.guardar(socio); //puede lanzar un error
            
            //asociamos el socio al usuario logueado:
            Usuario usuario = usuarioBean.getUsuarioLogueado();
            usuario.setSocio(socio);  //seteamos el socio
            usuarioServicio.actualizar(usuario);  //actualizamos la bd
            usuarioBean.setUsuario(usuario); //actualizamos el bean de usuario 
            usuarioBean.setUsuarioLogueado(usuario);
            // para que sepa que ya tiene un socio asociado
            

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Socio registrado correctamente",
                                    null
                            )
                    );
            
            
            socio = new Socio();  // vaciar el formulario
            //prestamoBean.setMostrarRegistrarSocio(false);
            

        } catch (ValidacionException e) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    null
                            )
                    );

        }

    }

    // getters y setters
    public SocioServicio getSocioServicio() {
        return socioServicio;
    }

    public void setSocioServicio(SocioServicio socioServicio) {
        this.socioServicio = socioServicio;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

}
