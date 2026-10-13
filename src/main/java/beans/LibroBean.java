/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Libro;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import servicios.LibroServicio;

/**
 *
 * @author timot
 */
@Named   //Permite usar #{libroBean} en los xhtml
@ViewScoped  //El Bean vive mientras el usuario siga en la misma vista
public class LibroBean implements Serializable {

    @Inject
    private LibroServicio libroServicio;  //Inyecta el servicio
    private List<Libro> libros;  //Lista de libros para mostrar en libros.xhtml
    private Libro libro;        //Libro actual para detalle.hxtml
    private String busqueda;    //Texto escrito en el buscador, se guarda aqui 
    private Integer idLibro;    //Recibe el id desde <f:viewParam>

    private boolean libroEncontrado;

    private Integer categoriaSeleccionada;

    //muestra solo 4 libros simulando los destacados
    private List<Libro> destacados;

    //para busqueda por autor
    private String autorBusqueda;

    @PostConstruct
    public void init() {
        cargarLibros();  //Al abrir la pagina carga todos los libros
        //apenas nazca este Bean, ejecuta eso.
        destacados = libroServicio.listarDestacados();
    }

    public void cargarLibros() {
        libros = libroServicio.listarTodos(); //obtenemos todos los libros de la BD 
        // y los guardamos en la lista "libros"
        categoriaSeleccionada = null;
    }

    /*Metodo que busca mediante el titulo*/
    public void buscar() {
        categoriaSeleccionada = null;
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            libros = libroServicio.buscarPorTitulo(busqueda);

            /*Busca los libros cuyo titulo coincida con lo escrito en el buscador*/
        } else {
            cargarLibros();
            /*Si el buscador vuelve a mostrar todo*/
        }
    }

    public void filtrarCategoria(Integer idCategoria) {
        categoriaSeleccionada = idCategoria;
        libros = libroServicio.buscarPorCategoria(idCategoria);
        /*Se guardan en la lista "libros" unicamente los libros de la categoria
        seleccionada*/
    }

    public void cargarLibro() {
        if (idLibro != null) {
            libro = libroServicio.buscarPorId(idLibro);

            /*Busca el libro recibido por URL, ej: detalle.xhtml?id=5
            
            Si lo encuentra, significa que se mando, que alguien presiono.. entonces
            detalle.xhtml lo toma mediante este bean y llama a getLibro() y
            lo carga en la vista para mostarlo.
            detalle.xhtml?id=5 => f:viewParam => setIdLibro(5) => cargarLibro() => libro = buscarPorId(5) => #{libroBean.libro.titulo}
             */
            libroEncontrado = libro != null;
        } else {
            libroEncontrado = false;
        }
    }

    //buscar por autor
    public void buscarAutor() {
        categoriaSeleccionada = null;
        if (autorBusqueda != null && !autorBusqueda.trim().isEmpty()) {
            libros = libroServicio.buscarPorAutor(autorBusqueda);
        } else {
            cargarLibros();
        }
    }

    // === getters y setters
    public LibroServicio getLibroServicio() {
        return libroServicio;
    }

    public void setLibroServicio(LibroServicio libroServicio) {
        this.libroServicio = libroServicio;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public boolean isLibroEncontrado() {
        return libroEncontrado;
    }

    public void setLibroEncontrado(boolean libroEncontrado) {
        this.libroEncontrado = libroEncontrado;
    }

    public Integer getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(Integer categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public List<Libro> getDestacados() {
        return destacados;
    }

    public void setDestacados(List<Libro> destacados) {
        this.destacados = destacados;
    }

    //buscar por autor
    public String getAutorBusqueda() {
        return autorBusqueda;
    }

    public void setAutorBusqueda(String autorBusqueda) {
        this.autorBusqueda = autorBusqueda;
    }

}
