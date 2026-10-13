/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Categoria;
import excepciones.ValidacionException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import servicios.CategoriaServicio;

/**
 *
 * @author timot
 */
@Named
@ViewScoped
public class CategoriaAdminBean implements Serializable {

    @Inject
    private CategoriaServicio categoriaServicio;

    private Categoria categoria;

    private List<Categoria> categorias;

    @PostConstruct
    public void init() {

        categoria = new Categoria();
        cargar();

    }

    public void cargar() {
        categorias = categoriaServicio.listarTodos();
    }

    public void guardar() {

        try {

            if (categoria.getId() == null) {

                categoriaServicio.guardar(categoria);

            } else {

                categoriaServicio.actualizar(categoria);

            }
            categoria = new Categoria();
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

    public void editar(Categoria categoria) {
        this.categoria = categoria;
    }

    // eliminar desde la BD PERO preguntando si tiene algun 
    //campos asociados
    public void eliminar(Categoria categoria) {

        try {

            categoriaServicio.eliminar(categoria.getId());

            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_INFO,
                                    "Categoría eliminada correctamente",
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
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

}
