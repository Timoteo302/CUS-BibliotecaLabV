/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.ManyToMany;

/**
 *
 * @author timot
 */
@Entity
@Table(name = "autores")
//@NamedQueries({
//    @NamedQuery(name = "Autores.findAll", query = "SELECT a FROM Autores a"),
//    @NamedQuery(name = "Autores.findById", query = "SELECT a FROM Autores a WHERE a.id = :id"),
//    @NamedQuery(name = "Autores.findByApellido", query = "SELECT a FROM Autores a WHERE a.apellido = :apellido"),
//    @NamedQuery(name = "Autores.findByNombres", query = "SELECT a FROM Autores a WHERE a.nombres = :nombres")})
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "apellido")
    private String apellido;
    @Size(max = 255)
    @Column(name = "nombres")
    private String nombres;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor")
//    private Collection<Libro> librosCollection;
    
    // nuevo
    @ManyToMany(mappedBy = "autores")
    private Collection<Libro> librosCollection;

    public Autor() {
    }

    public Autor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Collection<Libro> getLibrosCollection() {
        return librosCollection;
    }

    public void setLibrosCollection(Collection<Libro> librosCollection) {
        this.librosCollection = librosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Autor[ id=" + id + " ]";
    }
    
}
