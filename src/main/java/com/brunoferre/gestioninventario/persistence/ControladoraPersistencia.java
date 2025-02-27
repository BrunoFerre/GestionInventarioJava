package com.brunoferre.gestioninventario.persistence;

import com.brunoferre.gestioninventario.logica.DetalleVenta;
import com.brunoferre.gestioninventario.logica.Persona;
import com.brunoferre.gestioninventario.logica.Producto;
import com.brunoferre.gestioninventario.logica.Venta;
import com.brunoferre.gestioninventario.logica.VentasDTO;
import com.brunoferre.gestioninventario.persistence.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
    
    public void guardarDetalleVenta(DetalleVenta detalleVenta) {
        try {
            detalleJpa.create(detalleVenta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void guardarVenta(Venta nuevaVenta) {
        try {
            ventaJpa.create(nuevaVenta);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void actualizar(Long idProducto, int stock) {
        try {
            productoJpa.actualizarStock(idProducto, stock);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    
    public Venta traerVentaId(Long venta) {
        try {
            return ventaJpa.findVenta(venta);
        } catch (Exception e) {
            return new Venta();
        }
    }
    
    public List<Venta> traerVentas() {
        try {
            return ventaJpa.findVentaEntities();
        } catch (Exception e) {
            return List.of(new Venta(0L, LocalDate.now(), "ERROR ERROR", 0.0));
        }
    }
    
    public VentasDTO traerPorTicket(String ticket) {
        Venta venta = ventaJpa.findVentaByNumeroVenta(ticket);
        return new VentasDTO(venta);
    }
    
    public List<DetalleVenta> getAllByVenta(Venta venta) {
        return detalleJpa.findAllByVenta(venta);
    }
    
    public Persona findByEmailAndDdni(String email, String dni) {
        return personaJpa.findByEmailAndDni(email, dni).orElse(null);
        
    }

    public void guardarPersona(Persona persona) {
        personaJpa.create(persona);
    }
}
