/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Consulta;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import servicios.ConsultaServicio;

/**
 *
 * @author timot
 */
@Named
@ViewScoped
public class ConsultaAdminBean implements Serializable {
    
    @Inject
    private ConsultaServicio consultaServicio;
    
    private Consulta consulta;
    
    private List<Consulta> consultas;
    
    
    @PostConstruct
    public void init() {

        consulta = new Consulta();
        cargar();

    }

    public void cargar() {
        consultas = consultaServicio.listarTodos();
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    
    
    
}
