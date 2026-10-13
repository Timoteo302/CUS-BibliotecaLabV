/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package convertidores;

/**
 *
 * @author timot
 */

import entidades.Autor;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import servicios.AutorServicio;

import entidades.Autor;

import jakarta.faces.component.UIComponent;

import entidades.Autor;


@FacesConverter(value = "autorConverter", managed = true)
public class AutorConverter implements Converter<Autor> {

    @Inject
    private AutorServicio servicio;

    @Override
    public Autor getAsObject(FacesContext context,
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
            Autor autor) {

        if (autor == null) {
            return "";
        }

        return autor.getId().toString();
    }

}
