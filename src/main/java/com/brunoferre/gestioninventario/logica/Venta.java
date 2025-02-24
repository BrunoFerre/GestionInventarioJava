package com.brunoferre.gestioninventario.logica;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Venta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    @Column(unique = true)
    private String numeroVenta;
    private Double total;
    @OneToMany(mappedBy = "venta", fetch = FetchType.EAGER)
    private Set<DetalleVenta> detalles = new HashSet<>();

    public Venta() {
    }

    public Venta(Long id, LocalDate fecha, String numeroVenta, Double total) {
        this.id = id;
        this.fecha = fecha;
        this.numeroVenta = numeroVenta;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(Set<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
    

    public void aniadirDetalleVenta(DetalleVenta detalleVenta) {
        detalleVenta.setVenta(this);
        this.detalles.add(detalleVenta);
    }

}
