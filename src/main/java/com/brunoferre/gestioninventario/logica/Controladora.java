package com.brunoferre.gestioninventario.logica;

import com.brunoferre.gestioninventario.persistence.ControladoraPersistencia;
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
}
