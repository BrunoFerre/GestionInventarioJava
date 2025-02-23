package com.brunoferre.gestioninventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    @Column(unique = true)
    private String numeroVenta;
    private Double total;
    @OneToMany(mappedBy = "venta", fetch = FetchType.EAGER)
    private Set<DetalleVenta> detalles = new HashSet<>();

    public void aniadirDetalleVenta(DetalleVenta detalleVenta) {
        detalleVenta.setVenta(this);
        this.detalles.add(detalleVenta);
    }

}
