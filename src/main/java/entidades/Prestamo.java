/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author timot
 */
@Entity
@Table(name = "prestamos")
//@NamedQueries({
//    @NamedQuery(name = "Prestamos.findAll", query = "SELECT p FROM Prestamos p"),
//    @NamedQuery(name = "Prestamos.findById", query = "SELECT p FROM Prestamos p WHERE p.id = :id"),
//    @NamedQuery(name = "Prestamos.findByFecha", query = "SELECT p FROM Prestamos p WHERE p.fecha = :fecha"),
//    @NamedQuery(name = "Prestamos.findByFechaDevuelve", query = "SELECT p FROM Prestamos p WHERE p.fechaDevuelve = :fechaDevuelve"),
//    @NamedQuery(name = "Prestamos.findByMulta", query = "SELECT p FROM Prestamos p WHERE p.multa = :multa")})
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    
    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    

//    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Column(name = "multa")
//    private Float multa;

    
    @JoinColumn(name = "socios_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Socio socio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prestamosId", fetch = FetchType.EAGER)
    private Collection<LibroPrestamo> librosprestamosCollection;

    public Prestamo() {
    }

    public Prestamo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

//    public Float getMulta() {
//        return multa;
//    }
//
//    public void setMulta(Float multa) {
//        this.multa = multa;
//    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Collection<LibroPrestamo> getLibrosprestamosCollection() {
        return librosprestamosCollection;
    }

    public void setLibrosprestamosCollection(Collection<LibroPrestamo> librosprestamosCollection) {
        this.librosprestamosCollection = librosprestamosCollection;
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
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Prestamo[ id=" + id + " ]";
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    

}
