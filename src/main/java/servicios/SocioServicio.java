/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Socio;
import excepciones.ValidacionException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.SocioDAO;

/**
 *
 * @author timot
 */
@Stateless
public class SocioServicio {

    @Inject
    private SocioDAO socioDAO;

    public List<Socio> listarTodos() {
        return socioDAO.listarTodos();
    }

    public Socio buscarPorId(Integer id) {
        return socioDAO.buscarPorId(id);
    }

    public Socio buscarPorDNI(Integer dni) throws ValidacionException {

        if (dni == null) {
            throw new ValidacionException("Debe ingresar un DNI");
        }

        return socioDAO.buscarPorDNI(dni);

    }

    public void guardar(Socio socio) throws ValidacionException {
        validarSocio(socio);
        // Si es que esta todo OK guardamos
        socioDAO.guardar(socio);
    }

    public void actualizar(Socio socio) throws ValidacionException {
        validarSocio(socio);
        socioDAO.actualizar(socio);
    }

    public void eliminar(Integer id) {
        socioDAO.eliminar(id);
    }

    public void validarSocio(Socio socio) throws ValidacionException {
        if (socio.getNombres() == null
                || socio.getNombres().length() < 3) {

            throw new ValidacionException("El nombre debe tener mínimo 3 caracteres");
        }

        if (socio.getApellido() == null
                || socio.getApellido().length() < 3) {

            throw new ValidacionException("El apellido debe tener minimo 3 caracteres");
        }

        if (socio.getDni() == null
                || socio.getDni() < 0) {
            throw new ValidacionException("DNI invalido");
        }

        if (socio.getTelefono() == null
                || socio.getTelefono().length() < 9) {
            throw new ValidacionException("Telefono invalido");
        }

        if (socio.getDireccion() == null
                || socio.getDireccion().length() < 3) {
            throw new ValidacionException("La direccion debe tener minimo 3 caracteres");
        }
    }

    // nuevo
    public List<Socio> listarMorosos() {

        return socioDAO.listarMorosos();

    }

}
