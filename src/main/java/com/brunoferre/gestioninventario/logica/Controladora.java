package com.brunoferre.gestioninventario.logica;

import com.brunoferre.gestioninventario.persistence.ControladoraPersistencia;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import java.util.List;

public class Controladora {

    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    public void guardarProducto(String nombre, int Stock, Double precio, String codigoProducto) {
        Producto newProducto = new Producto();
        newProducto.setNombre(nombre);
        newProducto.setStock(Stock);
        newProducto.setPrecio(precio);
        newProducto.setCodigoProducto(codigoProducto);
        controlPersis.guardarProducto(newProducto);
    }

    public List<Producto> todosLosProductos() {
        return controlPersis.listarTodos();
    }

    public void eliminarProducto(Long idselecionado) {
        controlPersis.borrarProducto(idselecionado);
    }

    public Object buscarProductoId(Long id) {
        try {
            Producto producto = controlPersis.traerPorId(id);
            return producto;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Object buscarProductoNombre(String nombre) {
        try {
            Producto producto = controlPersis.traerPorNombre(nombre);
            return producto;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Object buscarProductoCodigo(String codigo) {
        try {
            Producto producto = controlPersis.traerPorCodigo(codigo);
            return producto;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Producto> productosFaltantes() {
        try {
            List<Producto> lista = controlPersis.productosFaltantes();
            return lista;
        } catch (Exception e) {
            Producto producto = new Producto("ERRROR", 0, 0.0);
            return List.of(producto);
        }
    }
}
