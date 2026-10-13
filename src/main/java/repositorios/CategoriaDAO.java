/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Categoria;
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
public class CategoriaDAO {
    
    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    
    // litarTodos
    public List<Categoria> listarTodos() {
        return em.createQuery("SELECT a FROM Categoria a", Categoria.class)
                .getResultList();
    }
    
    //buscarPorId
    public Categoria buscarPorId(Integer id){
        return em.find(Categoria.class, id);
    }
    
    //guardar
    public void guardar(Categoria categoria){
        em.persist(categoria);
    }
    
    //actualizar
    public void actualizar(Categoria categoria){
        em.merge(categoria);
    }
    
    //eliminar
    public void eliminar(Integer id){
        Categoria categoria = em.find(Categoria.class, id);
        
        if(categoria != null){
            em.remove(categoria);
        }
    }
}
