/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.brunoferre.gestioninventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bruno
 */
public class Producto {

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

    public void aniadirDetalle(DetalleVenta detalleVenta) {
        detalleVenta.setProducto(this);
        this.detalles.add(detalleVenta);
    }

}
