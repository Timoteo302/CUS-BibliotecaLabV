/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Consulta;
import excepciones.ValidacionException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.ConsultaDAO;

/**
 *
 * @author timot
 */
@Stateless
public class ConsultaServicio {

    @Inject
    private ConsultaDAO consultaDAO;

    public List<Consulta> listarTodos() {
        return consultaDAO.listarTodos();
    }

    public Consulta buscarPorId(Integer id) {
        return consultaDAO.buscarPorId(id);
    }

    /*El servicio si encuentra un error no lo muestra en pantalla
    automaticamente. El servicio no conoce JSF. Esta separado.
    Entonces si hay un error, se lanza una excepcion y eso vuelve hasta el Bean
    Entonces el bean debe capturarlo y mostrarlo.*/
//    public void guardar(Consulta consulta) {
//        
//        //validar nombre
//        if(consulta.getNombreCompleto() == null ||
//            consulta.getNombreCompleto().length() < 3){
//            
//            throw new IllegalArgumentException("El nombre debe tener minimo 3 caracteres");
//        }
//        
//        //validar mensaje
//        if(consulta.getConsulta() == null ||
//            consulta.getConsulta().length() < 10){
//            
//            throw new IllegalArgumentException("La consulta es demasiado corta");
//        }
//        
//        // validar email simple
//        if(consulta.getEmail() == null ||
//            !consulta.getEmail().contains("@") || 
//            !consulta.getEmail().contains(".")){
//            
//            throw new IllegalArgumentException("Email invalido");
//        }
//        
//        
//        // si paso todo guardamos
//        consultaDAO.guardar(consulta);
//    }
    public void actualizar(Consulta consulta) {
        consultaDAO.actualizar(consulta);
    }

    public void eliminar(Integer id) {
        consultaDAO.eliminar(id);
    }

    // SIN TRY/CATCH
//    public String validar(Consulta consulta) {
//
//        if (consulta.getNombreCompleto() == null
//                || consulta.getNombreCompleto().length() < 3) {
//
//            return "El nombre debe tener mínimo 3 caracteres";
//        }
//
//        if (consulta.getConsulta() == null
//                || consulta.getConsulta().length() < 10) {
//
//            return "La consulta es demasiado corta";
//        }
//
//        if (consulta.getEmail() == null
//                || !consulta.getEmail().contains("@")
//                || !consulta.getEmail().contains(".")) {
//
//            return "Email inválido debe contener @ y .";
//        }
//
//        return null;
//    }
//
//    public void guardar(Consulta consulta) {
//        consultaDAO.guardar(consulta);
//    }
    /* CON MIS PROPIAS EXCEPCIONES */
    public void guardar(Consulta consulta) throws ValidacionException {

        if (consulta.getNombreCompleto() == null || 
            consulta.getNombreCompleto().length() < 3) {

            throw new ValidacionException("El nombre debe tener mínimo 3 caracteres");
        }

        if (consulta.getConsulta() == null || 
            consulta.getConsulta().length() < 10) {

            throw new ValidacionException("La consulta es demasiado corta");
        }

        if (consulta.getEmail() == null
            || !consulta.getEmail().contains("@")
            || !consulta.getEmail().contains(".")) {

            throw new ValidacionException("Email inválido, debe contener @ y .");
        }

        // Si es que esta todo OK guardamos
        consultaDAO.guardar(consulta); 
    }
}
