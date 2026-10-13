/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Autor;
import excepciones.ValidacionException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.AutorDAO;

/**
 *
 * @author timot
 */
@Stateless
public class AutorServicio {

    /*
    Significa que necesita que le den un objeto de esa clase, por asi decirlo.
    Sin esa anotacion tendriamos que hacerlo manualmente asi:
    - UsuarioService usuarioService = new UsuarioService();
     */
    @Inject
    private AutorDAO autorDAO;

    public List<Autor> listarTodos() {
        return autorDAO.listarTodos();
    }

    public Autor buscarPorId(Integer id) {
        return autorDAO.buscarPorId(id);
    }

    public void guardar(Autor autor) throws ValidacionException {
        validarAutor(autor);
        autorDAO.guardar(autor);
    }

    public void actualizar(Autor autor) throws ValidacionException {
        validarAutor(autor);
        autorDAO.actualizar(autor);
    }

    public void eliminar(Integer id) throws ValidacionException {

        Autor autor = autorDAO.buscarPorId(id);

        if (autor == null) {
            throw new ValidacionException("El autor no existe");
        }

        if (!autor.getLibrosCollection().isEmpty()) {
            throw new ValidacionException(
                    "No se puede eliminar el autor porque tiene libros asociados"
            );
        }

        autorDAO.eliminar(id);
    }

    public void validarAutor(Autor a) throws ValidacionException {
        if (a.getNombres() == null || a.getNombres().isBlank()) {

            throw new ValidacionException("El nombre es obligatorio");
        }

        if (a.getApellido() == null || a.getApellido().isBlank()) {

            throw new ValidacionException("El apellido es obligatorio");
        }
    }

}
