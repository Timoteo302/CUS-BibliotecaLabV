/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author timot
 */
import entidades.Usuario;
import excepciones.ValidacionException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;

import java.io.IOException;
import java.io.Serializable;

import servicios.UsuarioServicio;

@Named
@SessionScoped  //Dura el beans mientras el usuario tenga sesión abierta.
public class UsuarioBean implements Serializable {

    @Inject
    private UsuarioServicio usuarioServicio;

    private Usuario usuario;

    private String usuarioLogin;
    private String passwordLogin;

    private Usuario usuarioLogueado;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    // se crea una cuenta
    public void registrar() {

        try {

            usuarioServicio.registrar(usuario);

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Cuenta creada correctamente",
                                    null
                            ));

            usuario = new Usuario();

        } catch (ValidacionException e) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    null
                            ));

        }

    }

    // se inicia sesion
    public String login() {

        try {

            usuarioLogueado = usuarioServicio.login(
                    usuarioLogin,
                    passwordLogin);

            if (usuarioLogueado.getRol().equals("ADMIN")) {

                return "/admin/prestamosA?faces-redirect=true";
            }

            return "/index?faces-redirect=true";

        } catch (ValidacionException e) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    null
                            ));

            return null;

        }

    }

    // salir de la sesion
    public void logout() throws IOException {

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();  //destruye la sesion
        // o sea quedan vacias las variables, sesion vacia, borra todo

//        FacesContext.getCurrentInstance()
//                .getExternalContext()
//                .redirect("auth/login.xhtml"); //despues redireciconamos
        FacesContext.getCurrentInstance().getExternalContext().redirect(
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRequestContextPath()
                + "/index.xhtml"
        );

    }

    //si es ADMIN o USUARIO
    public boolean esAdmin() {
        return usuarioLogueado != null
                && usuarioLogueado.getRol().equals("ADMIN");
    }

    // getters y setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

}
