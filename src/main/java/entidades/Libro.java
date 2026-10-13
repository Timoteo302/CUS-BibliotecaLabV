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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author timot
 */
@Entity
@Table(name = "libros")
//@NamedQueries({
//    @NamedQuery(name = "Libros.findAll", query = "SELECT l FROM Libros l"),
//    @NamedQuery(name = "Libros.findById", query = "SELECT l FROM Libros l WHERE l.id = :id"),
//    @NamedQuery(name = "Libros.findByTitulo", query = "SELECT l FROM Libros l WHERE l.titulo = :titulo"),
//    @NamedQuery(name = "Libros.findByYear", query = "SELECT l FROM Libros l WHERE l.year = :year"),
//    @NamedQuery(name = "Libros.findByEdicion", query = "SELECT l FROM Libros l WHERE l.edicion = :edicion"),
//    @NamedQuery(name = "Libros.findByEditorial", query = "SELECT l FROM Libros l WHERE l.editorial = :editorial"),
//    @NamedQuery(name = "Libros.findByDisponibles", query = "SELECT l FROM Libros l WHERE l.disponibles = :disponibles")})
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "year")
    private Integer year;
    @Column(name = "edicion")
    private Short edicion;
    @Size(max = 45)
    @Column(name = "editorial")
    private String editorial;
    @Lob
    @Size(max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "disponibles")
    private Integer disponibles;

//    @JoinColumn(name = "autores_id", referencedColumnName = "id")
//    @ManyToOne(optional = false)
//    private Autor autor;
    
    ///nuevo
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libros_id"),
            inverseJoinColumns = @JoinColumn(name = "autores_id")
    )
    private List<Autor> autores = new ArrayList<>();

    @JoinColumn(name = "categorias_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "librosId")
    private Collection<LibroPrestamo> librosprestamosCollection;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "activo")
    private Boolean activo = true;

    public Libro() {
    }

    public Libro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Short getEdicion() {
        return edicion;
    }

    public void setEdicion(Short edicion) {
        this.edicion = edicion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

//    public Autor getAutor() {
//        return autor;
//    }
//
//    public void setAutor(Autor autor) {
//        this.autor = autor;
//    }
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Collection<LibroPrestamo> getLibrosprestamosCollection() {
        return librosprestamosCollection;
    }

    public void setLibrosprestamosCollection(Collection<LibroPrestamo> librosprestamosCollection) {
        this.librosprestamosCollection = librosprestamosCollection;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
        if (!(object instanceof Libro)) {
            return false;
        }
        Libro other = (Libro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Libro[ id=" + id + " ]";
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    
    // nuevo
    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

}
