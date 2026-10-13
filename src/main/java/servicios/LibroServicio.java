/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Libro;
import excepciones.ValidacionException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.LibroDAO;

/**
 *
 * @author timot
 */
@Stateless
public class LibroServicio {

    @Inject
    private LibroDAO libroDAO;

    public List<Libro> listarTodos() {
        return libroDAO.listarTodos();
    }

    public Libro buscarPorId(Integer id) {
        return libroDAO.buscarPorId(id);
    }

    public void guardar(Libro libro) throws ValidacionException {

        // validamos
        validarLibro(libro);

        // si no hay error guardamos el libro
        libroDAO.guardar(libro);
    }

    public void actualizar(Libro libro) throws ValidacionException {
        validarLibro(libro);
        libro.setActivo(true);
        libroDAO.actualizar(libro);
    }

    //activar un libro
    public void activar(Integer id) {

        Libro libro = libroDAO.buscarPorId(id);

        libro.setActivo(true);

        libroDAO.actualizar(libro);
    }

    // baja logica! no elimina de la bd.
    public void eliminar(Integer id) {
        //libroDAO.eliminar(id);
        Libro libro = libroDAO.buscarPorId(id);

        libro.setActivo(false);

        libroDAO.actualizar(libro);
    }

    public void validarLibro(Libro libro) throws ValidacionException {

        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {

            throw new ValidacionException("El titulo es obligatorio");
        }

        if (libro.getEditorial() == null || libro.getEditorial().isBlank()) {

            throw new ValidacionException("El editorial es obligatorio");
        }

        if (libro.getDisponibles() < 0) {

            throw new ValidacionException(
                    "La cantidad disponible no puede ser negativa");
        }

        if (libro.getYear() < 1000) {

            throw new ValidacionException("Año inválido");
        }

//        if (libro.getAutor() == null) {
//
//            throw new ValidacionException("Debe seleccionar un autor");
//        }
        // NUEVO 2 AUTORES
        if (libro.getAutores() == null || libro.getAutores().isEmpty()) {

            throw new ValidacionException(
                    "Debe seleccionar al menos un autor");
        }

        if (libro.getAutores().size() > 2) {

            throw new ValidacionException(
                    "Solo puede seleccionar hasta 2 autores");
        }

        if (libro.getCategoria() == null) {

            throw new ValidacionException("Debe seleccionar una categoría");
        }
    }

    //
    public List<Libro> listarDestacados() {
        return libroDAO.listarDestacados();
    }
    //

    //
    public List<Libro> listarInactivos() {
        return libroDAO.listarInactivos();
    }
    //

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroDAO.buscarPorTitulo(titulo);
    }

    public List<Libro> buscarPorCategoria(Integer idCategoria) {
        return libroDAO.buscarPorCategoria(idCategoria);
    }
    
    public List<Libro> buscarPorAutor(String autor){
        return libroDAO.buscarPorAutor(autor);
    }
}
