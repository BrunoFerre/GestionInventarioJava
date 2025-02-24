package com.brunoferre.gestioninventario.logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(unique = true)
    private String codigoProducto;
    private int stock;
    private Double precio;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<DetalleVenta> detalles = new HashSet<>();

    public Producto() {
    }

    public Producto(String nombre, int stock, Double precio) {
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Set<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public void aniadirDetalle(DetalleVenta detalleVenta) {
        detalleVenta.setProducto(this);
        this.detalles.add(detalleVenta);
    }

}
