/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package convertidores;

import entidades.Categoria;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import servicios.CategoriaServicio;

@FacesConverter(value = "categoriaConverter", managed = true)
public class CategoriaConverter implements Converter<Categoria> {

    @Inject
    private CategoriaServicio servicio;

    @Override
    public Categoria getAsObject(FacesContext context,
            UIComponent component,
            String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        return servicio.buscarPorId(Integer.valueOf(value));

    }

    @Override
    public String getAsString(FacesContext context,
            UIComponent component,
            Categoria categoria) {

        if (categoria == null) {
            return "";
        }

        return categoria.getId().toString();

    }

}
