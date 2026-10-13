/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Consulta;
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
public class ConsultaDAO {
    
    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    
    // litarTodos
    public List<Consulta> listarTodos() {
        return em.createQuery("SELECT a FROM Consulta a", Consulta.class)
                .getResultList();
    }
    
    //buscarPorId
    public Consulta buscarPorId(Integer id){
        return em.find(Consulta.class, id);
    }
    
    //guardar
    public void guardar(Consulta consulta){
        em.persist(consulta);
    }
    
    //actualizar
    public void actualizar(Consulta consulta){
        em.merge(consulta);
    }
    
    //eliminar
    public void eliminar(Integer id){
        Consulta consulta = em.find(Consulta.class, id);
        
        if(consulta != null){
            em.remove(consulta);
        }
    }
}
