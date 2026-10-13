/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

/**
 *
 * @author timot
 */
import entidades.Consulta;
import excepciones.ValidacionException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import servicios.ConsultaServicio;

@Named
@ViewScoped
public class ConsultaBean implements Serializable {

    @Inject
    private ConsultaServicio consultaServicio;

    private Consulta consulta;

    @PostConstruct
    public void init() {
        consulta = new Consulta();
    }

//    public void guardar() {
//
//        try {
//
//            consultaServicio.guardar(consulta);
//
//            //creamos una nueva consulta para limpiar el formulario
//            consulta = new Consulta();
//
//            FacesContext.getCurrentInstance()
//                    .addMessage(null,
//                            new FacesMessage(
//                                    FacesMessage.SEVERITY_INFO,
//                                    "Consulta enviada correctamente",
//                                    null
//                            )
//                    );
//
//        } catch (IllegalArgumentException e) {
//            
//            System.out.println("ENTRO AL CATCH");
//
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
//                    FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
//
    
    
//            /*Esto lo que hace es mandarle un mensaje a la página XHTML.
//            Supongamos que un usuario pone mal el mail, sin "@". Entonces
//            el servicio da error, creamos un nuevo error. Que ese error sera capturado
//            aqui en este catch y lo que tenga con "e.getMessage()"
//            
//            -FacesContext.getCurrentInstance(): Dice: dame la pagina JSF que esta
//            siendo ejecutada ahora (contacto.xhtml)
//            
//            -.addMessage(null, mensaje): Agrega un mensaje para ponerlo en la pagina
//            El "null" es el id del componente, pero como tenemos <h:messages/> es sin id, por eso "null"
//            
//            -new FacesMessage(
//                FacesMessage.SEVERITY_ERROR, 
//                e.getMessage(), null)
//            Eso crea el mensaje. Seria como decir: (Tipo: ERROR, Texto: Email invalido)
//             */
//        }
//
//    }
    public ConsultaServicio getConsultaServicio() {
        return consultaServicio;
    }

    public void setConsultaServicio(ConsultaServicio consultaServicio) {
        this.consultaServicio = consultaServicio;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    // SIN TRY/CATCH
//    public void guardar() {
//
//        String error = consultaServicio.validar(consulta);
//
//        if (error != null) {
//
//            FacesContext.getCurrentInstance()
//                    .addMessage(null,
//                            new FacesMessage(
//                                    FacesMessage.SEVERITY_ERROR,
//                                    error,
//                                    null
//                            )
//                    );
//
//            return;
//        }
//
//        consultaServicio.guardar(consulta);  //guardamos la consulta
//
//        consulta = new Consulta();  //vaciamos el formulario
//
//        FacesContext.getCurrentInstance()
//                .addMessage(null,
//                        new FacesMessage(
//                                FacesMessage.SEVERITY_INFO,
//                                "Consulta enviada correctamente",
//                                null
//                        )
//                );
//
//    }
    
    
    
    // CON MI PROPIA EXCEPCIONES
    public void guardar() {

        try {

            consultaServicio.guardar(consulta); //puede lanzar un error
            
            consulta = new Consulta();  // vaciar el formulario

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Consulta enviada correctamente",
                                    null
                            )
                    );

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

}
