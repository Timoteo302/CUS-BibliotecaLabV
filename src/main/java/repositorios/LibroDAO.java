/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Libro;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * - Clase encargada de realizar operaciones sobre la base de datos (guardar,
 * buscar, modificar y eliminar datos). - DAO (Data Access Object): Clase
 * encargada de acceder a la base de datos. O sea, un DAO sabe hablar con la BD,
 * pero no debería tener lógica de negocio ni manejar pantallas.
 */
@Stateless
/*Sin estado, la clase no guarda datos entre peticiones, se crea => se usa => se destruye*/
public class LibroDAO {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;

    // litarTodos
    public List<Libro> listarTodos() {
        return em.createQuery("SELECT l FROM Libro l WHERE l.activo = true", Libro.class)
                .getResultList();
    }

    public List<Libro> listarInactivos() {
        return em.createQuery(
                "SELECT l FROM Libro l WHERE l.activo = false",
                Libro.class)
                .getResultList();
    }

    //buscarPorId
    public Libro buscarPorId(Integer id) {
        return em.find(Libro.class, id);
    }

    //guardar
    public void guardar(Libro libro) {
        em.persist(libro);
    }

    //actualizar
    public void actualizar(Libro libro) {
        em.merge(libro);
    }

    //eliminar
    public void eliminar(Integer id) {
        Libro libro = em.find(Libro.class, id);

        if (libro != null) {
            em.remove(libro);
        }
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return em.createQuery(
                "SELECT l FROM Libro l WHERE l.activo = true"
                + " AND LOWER(l.titulo) LIKE LOWER(:titulo)", Libro.class)
                .setParameter("titulo", "%" + titulo + "%")
                .getResultList();
    }

    public List<Libro> buscarPorCategoria(Integer idCategoria) {
        return em.createQuery(
                "SELECT l FROM Libro l WHERE l.activo = true AND l.categoria.id = :idCategoria", Libro.class)
                .setParameter("idCategoria", idCategoria)
                .getResultList();
    }

    //libros "populares" (4 libros random)
    public List<Libro> listarDestacados() {

        return em.createQuery(
                "SELECT l FROM Libro l WHERE l.activo = true ORDER BY FUNCTION('RAND')",
                Libro.class)
                .setMaxResults(4)
                .getResultList();
    }
    
    
    public List<Libro> buscarPorAutor(String autor) {
        return em.createQuery(
                "SELECT l FROM Libro l "
                + "JOIN l.autores a "
                + "WHERE l.activo = true "
                + "AND LOWER(a.nombres) LIKE LOWER(:autor)"
                , Libro.class)
                .setParameter("autor", "%" + autor + "%")
                .getResultList();
    }
}
