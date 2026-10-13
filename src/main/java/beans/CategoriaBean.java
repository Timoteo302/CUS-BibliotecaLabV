/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entidades.Categoria;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import servicios.CategoriaServicio;

/**
 *
 * @author timot
 */
@Named   //Permite usar #{categoriaBean} en los xhtml
@ViewScoped  //El Bean vive mientras el usuario siga en la misma vista
public class CategoriaBean implements Serializable {
    
    @Inject
    private CategoriaServicio categoriaServicio;
    
    private List<Categoria> categorias;
    
    @PostConstruct
    public void init(){
        cargarCategorias();
    }
    
    public void cargarCategorias(){
        categorias = categoriaServicio.listarTodos();
    }

    
    // getters y setters
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    
}
