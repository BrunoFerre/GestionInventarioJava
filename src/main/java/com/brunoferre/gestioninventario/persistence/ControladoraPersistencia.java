package com.brunoferre.gestioninventario.persistence;

import com.brunoferre.gestioninventario.logica.Producto;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {

    DetalleVentaJpaController detalleJpa = new DetalleVentaJpaController();
    PersonaJpaController personaJpa = new PersonaJpaController();
    ProductoJpaController productoJpa = new ProductoJpaController();
    VentaJpaController ventaJpa = new VentaJpaController();

    public void guardarProducto(Producto newProducto) {
        try {
            productoJpa.create(newProducto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Producto> listarTodos() {
        List<Producto> lista = productoJpa.findProductoEntities();
        return lista;
    }

    public void borrarProducto(Long idselecionado) {
        try {
            productoJpa.destroy(idselecionado);
        } catch (NonexistentEntityException e) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Producto traerPorId(Long id) {
        Producto producto = productoJpa.findProducto(id);
        return producto;
    }

    public Producto traerPorNombre(String nombre) {
        Producto producto = productoJpa.findProductoByNombre(nombre);
        return producto;
    }

    public Producto traerPorCodigo(String codigo) {
        Producto producto = productoJpa.findProductoByCodigo(codigo);
        return producto;
    }

    public List<Producto> productosFaltantes() {
        return productoJpa.findProductsForStock(20);
    }
}
