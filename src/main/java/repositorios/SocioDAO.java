/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Socio;
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
public class SocioDAO {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;

    // litarTodos
    public List<Socio> listarTodos() {
        return em.createQuery("SELECT a FROM Socio a", Socio.class)
                .getResultList();
    }

    //buscarPorId
    public Socio buscarPorId(Integer id) {
        return em.find(Socio.class, id);
    }

    public Socio buscarPorDNI(Integer dni) {
        return em.createQuery("SELECT s FROM Socio s WHERE s.dni = :dni", Socio.class)
                .setParameter("dni", dni)
                .getSingleResult();
    }

    //guardar
    public void guardar(Socio socio) {
        em.persist(socio);
    }

    //actualizar
    public void actualizar(Socio socio) {
        em.merge(socio);
    }

    //eliminar
    public void eliminar(Integer id) {
        Socio socio = em.find(Socio.class, id);

        if (socio != null) {
            em.remove(socio);
        }
    }

    // nuevo
    public List<Socio> listarMorosos() {

        String jpql
                = "SELECT DISTINCT s "
                + "FROM Socio s "
                + "JOIN s.prestamosCollection p "
                + "WHERE p.fechaDevolucion IS NULL "
                + "AND p.fechaVencimiento < CURRENT_DATE";

        return em.createQuery(jpql, Socio.class)
                .getResultList();
    }
}
