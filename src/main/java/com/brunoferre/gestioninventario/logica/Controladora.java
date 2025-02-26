package com.brunoferre.gestioninventario.logica;

import com.brunoferre.gestioninventario.persistence.ControladoraPersistencia;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

    public void guardarDetalle(DetalleVenta detalleVenta) {
        controlPersis.guardarDetalleVenta(detalleVenta);
    }

    public void guardarVenta(Venta nuevaVenta) {
        controlPersis.guardarVenta(nuevaVenta);
    }

    public void actualizar(Long idProducto, int stock) {
        controlPersis.actualizar(idProducto, stock);
    }

    public Venta taerVenta(Long venta) {
        return controlPersis.traerVentaId(venta);
    }

    public List<Venta> traerVentas() {
        return controlPersis.traerVentas();
    }

    public boolean verificarUsuario(String nombre, String dni) {
        return controlPersis.findPersonaByUsuario(nombre, dni);
    }

    public VentasDTO traerVentaporTicket(String ticket) {
        return controlPersis.traerPorTicket(ticket);
    }

    public List<DetallesVentaDTO> traerDetalles(Venta venta) {
        List<DetallesVentaDTO> detalle = controlPersis.getAllByVenta(venta).stream().map(dv -> new DetallesVentaDTO(dv)).toList();
        return detalle;
    }
}
