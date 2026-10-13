/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.LibroPrestamo;
import entidades.Prestamo;
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
public class PrestamoDAO {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;

    // litarTodos
    public List<Prestamo> listarTodos() {
        return em.createQuery("SELECT a FROM Prestamo a", Prestamo.class)
                .getResultList();
    }

    //buscarPorId
    public Prestamo buscarPorId(Integer id) {
        return em.find(Prestamo.class, id);
    }

    //guardar
    public void guardar(Prestamo prestamo) {
        em.persist(prestamo);
    }

    //actualizar
    public void actualizar(Prestamo prestamo) {
        em.merge(prestamo);
    }

    //eliminar
    public void eliminar(Integer id) {
        Prestamo prestamo = em.find(Prestamo.class, id);

        if (prestamo != null) {
            em.remove(prestamo);
        }
    }

    //buscar prestamos activos por socio:
    public List<Prestamo> buscarActivosPorSocio(Integer socioId) {

        return em.createQuery(
                "SELECT p FROM Prestamo p "
                + "WHERE p.socio.id = :id "
                + "AND p.fechaDevolucion IS NULL",
                Prestamo.class)
                .setParameter("id", socioId)
                .getResultList();
    }

    // ===== metodos backend ==== 
//    public List<Prestamo> listarActivos() {
//
//        return em.createQuery(
//                "SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NULL",
//                Prestamo.class)
//                .getResultList();
//
//    }
    public List<Prestamo> listarActivos() {

        return em.createQuery(
                "SELECT DISTINCT p FROM Prestamo p "
                + "LEFT JOIN FETCH p.librosprestamosCollection "
                + "WHERE p.fechaDevolucion IS NULL",
                Prestamo.class)
                .getResultList();

    }

//    public List<Prestamo> listarFinalizados() {
//
//        return em.createQuery(
//                "SELECT p FROM Prestamo p WHERE p.fechaDevolucion IS NOT NULL",
//                Prestamo.class)
//                .getResultList();
//
//    }
    public List<Prestamo> listarFinalizados() {

        return em.createQuery(
                "SELECT DISTINCT p FROM Prestamo p "
                + "LEFT JOIN FETCH p.librosprestamosCollection "
                + "WHERE p.fechaDevolucion IS NOT NULL",
                Prestamo.class)
                .getResultList();

    }

    // nuevos
    public List<LibroPrestamo> buscarPorSocio(Integer dniSocio) {

        String jpql
                = "SELECT lp FROM LibroPrestamo lp "
                + "WHERE lp.prestamosId.socio.dni = :dni";

        return em.createQuery(jpql, LibroPrestamo.class)
                .setParameter("dni", dniSocio)
                .getResultList();
    }

    // mas nuevos
    public List<LibroPrestamo> listarPrestamosVencidos() {

        String jpql
                = "SELECT lp FROM LibroPrestamo lp "
                + "WHERE lp.prestamosId.fechaDevolucion IS NULL "
                + "AND lp.prestamosId.fechaVencimiento < CURRENT_DATE";

        return em.createQuery(jpql, LibroPrestamo.class)
                .getResultList();
    }

    //libros mas prestados
    public List<Object[]> librosMasPrestados() {

        String jpql
                = "SELECT lp.librosId.titulo, COUNT(lp) "
                + "FROM LibroPrestamo lp "
                + "GROUP BY lp.librosId.titulo "
                + "ORDER BY COUNT(lp) DESC";

        return em.createQuery(jpql)
                .getResultList();

    }

    //socios con mas prestamos
    public List<Object[]> sociosConMasPrestamos() {

        String jpql
                = "SELECT s.nombres, s.apellido, COUNT(p) "
                + "FROM Prestamo p "
                + "JOIN p.socio s "
                + "GROUP BY s.id, s.nombres, s.apellido "
                + "ORDER BY COUNT(p) DESC";

        return em.createQuery(jpql)
                .getResultList();

    }

    // Nuevo listar morosos pero con datos de prestamo
    public List<LibroPrestamo> listarMorososPrestamo() {

        String jpql
                = "SELECT lp FROM LibroPrestamo lp "
                + "WHERE lp.prestamosId.fechaDevolucion IS NULL "
                + "AND lp.prestamosId.fechaVencimiento < CURRENT_DATE";

        return em.createQuery(jpql, LibroPrestamo.class)
                .getResultList();
    }
}
