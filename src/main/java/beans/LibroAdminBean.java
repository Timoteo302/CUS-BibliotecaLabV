/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Autor;
import entidades.Categoria;
import entidades.Libro;
import excepciones.ValidacionException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import servicios.AutorServicio;
import servicios.CategoriaServicio;
import servicios.LibroServicio;

/**
 *
 * @author timot
 */
@Named
@ViewScoped
public class LibroAdminBean implements Serializable {

    @Inject
    private LibroServicio libroServicio;

    @Inject
    private AutorServicio autorServicio;

    @Inject
    private CategoriaServicio categoriaServicio;

    private Libro libro;

    private List<Libro> libros;

    private List<Autor> autores;

    private List<Categoria> categorias;

    private List<Libro> librosInactivos;

    //NUEVO 2 AUTORES
    private List<Autor> autoresSeleccionados = new ArrayList<>();

    @PostConstruct
    public void init() {

        libro = new Libro();

        cargar();

    }

    public void cargar() {

        libros = libroServicio.listarTodos();

        autores = autorServicio.listarTodos();

        categorias = categoriaServicio.listarTodos();

        librosInactivos = libroServicio.listarInactivos();

    }

    //GUARDAR
    public void guardar() {
        System.out.println("ENTRO A GUARDAR");

        try {

            if (libro.getId() == null) {

                libro.setAutores(autoresSeleccionados);// NUEVO 2 AUTORES
                libroServicio.guardar(libro);

            } else {

                libro.setAutores(autoresSeleccionados); //NUEVO 2 AUTORES
                libroServicio.actualizar(libro);

            }

            libro = new Libro();
            autoresSeleccionados = new ArrayList<>(); //NUEVO 2 AUTORES
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

    // ELIMINAR
    public void eliminar(Libro libro) {
        libroServicio.eliminar(libro.getId());
        cargar();
    }

    //EDITAR
//    public void editar(Libro libro) {
//        this.libro = libro;
//    }
    // NUEVO 2 AUTORES
    public void editar(Libro libro) {
        this.libro = libro;
        this.autoresSeleccionados = new ArrayList<>(libro.getAutores());
    }

    //-----------
    public void activar(Libro libro) {

        libroServicio.activar(libro.getId());

        cargar();
    }

    // getters y setters
    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Libro> getLibrosInactivos() {
        return librosInactivos;
    }

    public void setLibrosInactivos(List<Libro> librosInactivos) {
        this.librosInactivos = librosInactivos;
    }

    // NUEVO 2 AUTORES
    public List<Autor> getAutoresSeleccionados() {
        return autoresSeleccionados;
    }

    public void setAutoresSeleccionados(List<Autor> autoresSeleccionados) {
        this.autoresSeleccionados = autoresSeleccionados;
    }
}
