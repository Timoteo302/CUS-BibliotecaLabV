/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Categoria;
import excepciones.ValidacionException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.CategoriaDAO;

/**
 *
 * @author timot
 */
@Stateless
public class CategoriaServicio {

    @Inject
    private CategoriaDAO categoriaDAO;

    public List<Categoria> listarTodos() {
        return categoriaDAO.listarTodos();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaDAO.buscarPorId(id);
    }

    public void guardar(Categoria categoria) throws ValidacionException {
        validarCategoria(categoria);
        categoriaDAO.guardar(categoria);
    }

    public void actualizar(Categoria categoria) throws ValidacionException {
        validarCategoria(categoria);
        categoriaDAO.actualizar(categoria);
    }

    public void eliminar(Integer id) throws ValidacionException {

        Categoria categoria = categoriaDAO.buscarPorId(id);

        if (categoria == null) {
            throw new ValidacionException("La categoría no existe");
        }

        if (!categoria.getLibrosCollection().isEmpty()) {
            throw new ValidacionException(
                    "No se puede eliminar la categoría porque tiene libros asociados");
        }

        categoriaDAO.eliminar(id);
    }

    public void validarCategoria(Categoria c) throws ValidacionException {

        if (c.getNombre() == null || c.getNombre().isBlank()) {

            throw new ValidacionException("El nombre es obligatorio");
        }

    }

}
