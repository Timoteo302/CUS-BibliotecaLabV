/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Autor;
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


/*
Habla con la BD, Aca van las operaciones CRUD
metodos que casi siempre tendremos: listarTodos, buscarPorId, guardar, actualizar, eliminar
*/
@Stateless   /*Sin estado, la clase no guarda datos entre peticiones, se crea => se usa => se destruye*/
public class AutorDAO {
    
    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    
    // litarTodos
    public List<Autor> listarTodos() {
        return em.createQuery("SELECT a FROM Autor a", Autor.class)
                .getResultList();
    }
    
    //buscarPorId
    public Autor buscarPorId(Integer id){
        return em.find(Autor.class, id);
    }
    
    //guardar
    public void guardar(Autor autor){
        em.persist(autor);
    }
    
    //actualizar
    public void actualizar(Autor autor){
        em.merge(autor);
    }
    
    //eliminar
    public void eliminar(Integer id){
        Autor autor = em.find(Autor.class, id);
        
        if(autor != null){
            em.remove(autor);
        }
    }
}
