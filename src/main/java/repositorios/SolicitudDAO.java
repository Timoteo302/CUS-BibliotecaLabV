/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Solicitud;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author timot
 */
@Stateless
/*Sin estado, la clase no guarda datos entre peticiones, se crea => se usa => se destruye*/
public class SolicitudDAO {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;

    // litarTodos
    public List<Solicitud> listarTodos() {
        return em.createQuery("SELECT s FROM Solicitud s", Solicitud.class)
                .getResultList();
    }

    //buscar por socio
    //backend
    @Transactional
    public Solicitud buscarPorSocio(Integer socioId) {

        try {

            return em.createQuery(
                    "SELECT s FROM Solicitud s WHERE s.socio.id = :socioId ORDER BY s.id DESC",
                    Solicitud.class)
                    .setParameter("socioId", socioId)
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (Exception e) {

            return null;
        }
    }


    //buscarPorId
    public Solicitud buscarPorId(Integer id) {
        return em.find(Solicitud.class, id);
    }

    //guardar
    public void guardar(Solicitud solicitud) {
        em.persist(solicitud);
    }

    //eliminar
    public void eliminar(Solicitud solicitud) {

        Solicitud s = em.find(Solicitud.class, solicitud.getId());

        if (s != null) {
            em.remove(s);
        }
    }
}
