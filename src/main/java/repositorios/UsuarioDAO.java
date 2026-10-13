/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Usuario;
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
public class UsuarioDAO {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;

    // litarTodos
    public List<Usuario> listarTodos() {
        return em.createQuery("SELECT a FROM Usuario a", Usuario.class)
                .getResultList();
    }

    //buscarPorId
    public Usuario buscarPorId(Integer id) {
        return em.find(Usuario.class, id);
    }

    //guardar
    public void guardar(Usuario usuario) {
        em.persist(usuario);
    }

    //actualizar
    public void actualizar(Usuario usuario) {
        em.merge(usuario);
    }

    //eliminar
    public void eliminar(Integer id) {
        Usuario usuario = em.find(Usuario.class, id);

        if (usuario != null) {
            em.remove(usuario);
        }
    }

    //buscar por usuario
    public Usuario buscarPorUsuario(String usuario) {

        try {

            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.usuario = :usuario",
                    Usuario.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();

        } catch (Exception e) {

            return null;
        }
    }

    // listar usuarios que todavía no tienen socio
    public List<Usuario> listarSinSocio() {

        return em.createQuery(
                "SELECT u FROM Usuario u WHERE u.socio IS NULL AND u.rol <> 'ADMIN'",
                Usuario.class)
                .getResultList();
    }
}
