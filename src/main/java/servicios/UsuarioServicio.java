/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import entidades.Usuario;
import excepciones.ValidacionException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import repositorios.UsuarioDAO;

/**
 *
 * @author timot
 */
@Stateless
public class UsuarioServicio {

    @Inject
    private UsuarioDAO usuarioDAO;

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioDAO.buscarPorId(id);
    }

    public void guardar(Usuario usuario) {
        usuarioDAO.guardar(usuario);
    }

    public void actualizar(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    public void eliminar(Integer id) {
        usuarioDAO.eliminar(id);
    }

    //registrar un usuario
    public void registrar(Usuario usuario) throws ValidacionException {

        if (usuario.getUsuario() == null || usuario.getUsuario().isBlank()) {

            throw new ValidacionException("El usuario es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().length() < 4) {

            throw new ValidacionException("La contraseña debe tener mínimo 4 caracteres");
        }

        Usuario existe = usuarioDAO.buscarPorUsuario(usuario.getUsuario());

        if (existe != null) {

            throw new ValidacionException("Ese usuario ya existe");

        }

        usuario.setRol("USUARIO");
        usuarioDAO.guardar(usuario);

    }

    //guardar el usuario
    public Usuario login(String nombreUsuario, String password)
            throws ValidacionException {

        Usuario usuario = usuarioDAO.buscarPorUsuario(nombreUsuario);

        if (usuario == null) {

            throw new ValidacionException("Usuario inexistente");

        }

        if (!usuario.getPassword().equals(password)) {

            throw new ValidacionException("Contraseña incorrecta");

        }

        return usuario;

    }

    public List<Usuario> listarSinSocio() {
        return usuarioDAO.listarSinSocio();
    }

}
