/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author timot
 */
@Entity
@Table(name = "librosprestamos")
//@NamedQueries({
//    @NamedQuery(name = "Librosprestamos.findAll", query = "SELECT l FROM Librosprestamos l"),
//    @NamedQuery(name = "Librosprestamos.findById", query = "SELECT l FROM Librosprestamos l WHERE l.id = :id")})
public class LibroPrestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "libros_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Libro librosId;
    @JoinColumn(name = "prestamos_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prestamo prestamosId;

    // agregamos 
    @Column(name = "devuelto")
    private Boolean devuelto = false;

    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;

    @Column(name = "multa")
    private Float multa;

    public LibroPrestamo() {
    }

    public LibroPrestamo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getLibrosId() {
        return librosId;
    }

    public void setLibrosId(Libro librosId) {
        this.librosId = librosId;
    }

    public Prestamo getPrestamosId() {
        return prestamosId;
    }

    public void setPrestamosId(Prestamo prestamosId) {
        this.prestamosId = prestamosId;
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
        if (!(object instanceof LibroPrestamo)) {
            return false;
        }
        LibroPrestamo other = (LibroPrestamo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.LibroPrestamo[ id=" + id + " ]";
    }

    public Boolean getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Boolean devuelto) {
        this.devuelto = devuelto;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Float getMulta() {
        return multa;
    }

    public void setMulta(Float multa) {
        this.multa = multa;
    }

}
