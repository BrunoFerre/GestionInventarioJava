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

    public Persona iniciarSesion(String email, String dni) {
        System.out.println("Iniciando sesión con Email: " + email + ", DNI: " + dni);

        Persona persona = controlPersis.findByEmailAndDdni(email, dni);

        if (persona != null) {
            System.out.println("Usuario encontrado: " + persona.getEmail());
            return persona; // ⚠️ Verifica si la contraseña está encriptada
        }

        System.out.println("Usuario NO encontrado");
        return null;
    }

    public VentasDTO traerVentaporTicket(String ticket) {
        return controlPersis.traerPorTicket(ticket);
    }

    public List<DetallesVentaDTO> traerDetalles(Venta venta) {
        List<DetallesVentaDTO> detalle = controlPersis.getAllByVenta(venta).stream().map(dv -> new DetallesVentaDTO(dv)).toList();
        return detalle;
    }

    public void guardarPersona(Persona persona) {
        controlPersis.guardarPersona(persona);
    }
}
