/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.LibroPrestamo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.LibroPrestamoDAO;

/**
 *
 * @author timot
 */
@Stateless
public class LibroPrestamoServicio {

    @Inject
    private LibroPrestamoDAO libroPreDAO;

    public List<LibroPrestamo> listarTodos() {
        return libroPreDAO.listarTodos();
    }

    public LibroPrestamo buscarPorId(Integer id) {
        return libroPreDAO.buscarPorId(id);
    }

    public void guardar(LibroPrestamo libroPre) {
        libroPreDAO.guardar(libroPre);
    }

    public void actualizar(LibroPrestamo libroPre) {
        libroPreDAO.actualizar(libroPre);
    }

    public void eliminar(Integer id) {
        libroPreDAO.eliminar(id);
    }

}
