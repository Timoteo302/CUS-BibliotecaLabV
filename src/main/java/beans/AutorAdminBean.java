/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Autor;
import excepciones.ValidacionException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import servicios.AutorServicio;

/**
 *
 * @author timot
 */
@Named
@ViewScoped
public class AutorAdminBean implements Serializable {

    @Inject
    private AutorServicio autorServicio; //servicio

    private Autor autor;  // entidad

    //obtenemos todos los autores
    private List<Autor> autores;

    @PostConstruct
    public void init() {

        autor = new Autor();
        cargar();

    }

    public void cargar() {
        autores = autorServicio.listarTodos();
    }

    public void guardar() {

        try {

            if (autor.getId() == null) {

                autorServicio.guardar(autor);

            } else {

                autorServicio.actualizar(autor);

            }
            autor = new Autor();
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
                                    null
                            ));

        }

    }

    public void editar(Autor autor) {
        this.autor = autor;
    }

    // eliminar desde la BD PERO preguntando si tiene algun 
    //campos asociados
    public void eliminar(Autor autor) {

        try {

            autorServicio.eliminar(autor.getId());

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Autor eliminado correctamente",
                                    null));

            cargar();

        } catch (ValidacionException e) {

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    null));
        }

    }

    // getters
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

}
