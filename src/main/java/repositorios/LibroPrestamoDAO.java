/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.LibroPrestamo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;


/**
- Clase encargada de realizar operaciones sobre la base de datos 
 (guardar, buscar, modificar y eliminar datos).
- DAO (Data Access Object): Clase encargada de acceder a la 
 base de datos. O sea, un DAO sabe hablar con la BD, pero no 
 debería tener lógica de negocio ni manejar pantallas.
 */

@Stateless   /*Sin estado, la clase no guarda datos entre peticiones, se crea => se usa => se destruye*/
public class LibroPrestamoDAO {
    
    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    
    // litarTodos
    public List<LibroPrestamo> listarTodos() {
        return em.createQuery("SELECT a FROM LibroPrestamo a", LibroPrestamo.class)
                .getResultList();
    }
    
    //buscarPorId
    public LibroPrestamo buscarPorId(Integer id){
        return em.find(LibroPrestamo.class, id);
    }
    
    //guardar
    public void guardar(LibroPrestamo libroPre){
        em.persist(libroPre);
    }
    
    //actualizar
    public void actualizar(LibroPrestamo libroPre){
        em.merge(libroPre);
    }
    
    //eliminar
    public void eliminar(Integer id){
        LibroPrestamo libroPre = em.find(LibroPrestamo.class, id);
        
        if(libroPre != null){
            em.remove(libroPre);
        }
    }
}
