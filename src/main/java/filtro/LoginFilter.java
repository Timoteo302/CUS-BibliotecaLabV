/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtro;

import beans.UsuarioBean;
import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.Filter;

/**
 *
 * @author timot
 */
@WebFilter("*.xhtml")  //significa que esta clase se ejecuta antes 
//de abrir cualquier pagina hxtml
public class LoginFilter implements Filter {

    /*con "Filter" estamos diciendo que es un filtro de Java EE
    Un filtro es como un guardia en la puerta*/
    @Inject
    UsuarioBean usuarioBean;

    /* Inject obtiene una instancia administrada por
    CDI inyecta automáticamente una instancia del UsuarioBean
    en esta clase para poder usar sus métodos y datos.
    
    No creamos el objeto manualmente con new, sino que se mantiene
    con vida*/

 /*CDI (Contexts and Dependency Injection) es una herramienta 
    de Jakarta EE que crea y conecta automáticamente objetos 
    entre clases usando anotaciones como @Inject.*/
 /*doFilter: Es lo que se ejecuta siempre cada vez que alguien pide una pagina*/
//    public void doFilter(ServletRequest req,
//            ServletResponse res,
//            FilterChain chain)
//            throws IOException, ServletException {
//
//        /*Request:
//                Aqui se convierte la peticion normal en una peticion HTTP
//                Por que? Porque necesitamos cosas como: 
//                --- request.getRequeestURL() --- que nos dice que pagina se pidió
//                y es lo usamos para verificar:
//                1. si el usuario no esta logueado:
//                2. si la pagina a la que se entro NO es login o registrar
//                => entonces se lo redirecciona a "login.xhtml"
//                
//                Y el Response:
//                Es para responder. por ejemplo:
//                --- response.sendRedirect() --- manda al usuario a otro lugar*/
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//
//        String uri = request.getRequestURI();
//
//        if ((usuarioBean == null || usuarioBean.getUsuarioLogueado() == null)
//                && !uri.contains("/auth/login.xhtml")
//                && !uri.contains("/auth/registroUsuario.xhtml")
//                && !uri.contains("jakarta.faces.resource")) {
//
//            response.sendRedirect(request.getContextPath()
//                    + "/auth/login.xhtml");
//
//            return;
//
//        }
//        // LOGUEADO PERO NO ADMIN
//        if (uri.contains("/admin/")) {
//
//            if (usuarioBean == null
//                    || !usuarioBean.esAdmin()) {
//
//                response.sendRedirect(
//                        request.getContextPath() + "/index.xhtml"
//                );
//
//                return;
//            }
//        }
//
//        /*como que dice "ok, puede ver la pagina"
//        chain.doFilter(req,res) permite que la petición continúe 
//        al siguiente filtro o a la página solicitada. 
//        Si no se llama, la página queda bloqueada.
//         */
//        chain.doFilter(req, res);
//
//    }
    //
    //
    //
    //
    // CAMBIO PARA QUE CUALQUIER USUARIO ENTRE A LA PAGINA 
    // TENGA O NO CUENTA. EL METODO DE ARRIBA ES EL MISMO
    // PERO SI O SI DEBES TENER CUENTA
    public void doFilter(ServletRequest req,
            ServletResponse res,
            FilterChain chain)
            throws IOException, ServletException {

        /*Request:
                Aqui se convierte la peticion normal en una peticion HTTP
                Por que? Porque necesitamos cosas como: 
                --- request.getRequeestURL() --- que nos dice que pagina se pidió
                y es lo usamos para verificar:
                1. si el usuario no esta logueado:
                2. si la pagina a la que se entro NO es login o registrar
                => entonces se lo redirecciona a "login.xhtml"
                
                Y el Response:
                Es para responder. por ejemplo:
                --- response.sendRedirect() --- manda al usuario a otro lugar*/
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();

        if (uri.contains("/admin/")
                && (usuarioBean == null
                || usuarioBean.getUsuarioLogueado() == null
                || !usuarioBean.esAdmin())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/auth/login.xhtml");

            return;
        }
        // LOGUEADO PERO NO ADMIN
        if (uri.contains("/admin/")) {

            if (usuarioBean == null
                    || !usuarioBean.esAdmin()) {

                response.sendRedirect(
                        request.getContextPath() + "/index.xhtml"
                );

                return;
            }
        }

        /*como que dice "ok, puede ver la pagina"
        chain.doFilter(req,res) permite que la petición continúe 
        al siguiente filtro o a la página solicitada. 
        Si no se llama, la página queda bloqueada.
         */
        chain.doFilter(req, res);

    }

}
