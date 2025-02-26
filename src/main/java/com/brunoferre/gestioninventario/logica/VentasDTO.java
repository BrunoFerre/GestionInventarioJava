package com.brunoferre.gestioninventario.logica;

import java.time.LocalDate;

public class VentasDTO {

    private Long id;
    private LocalDate fechaDeVenta;
    private String ticket;
    private Double total;

    public VentasDTO() {
    }

    public VentasDTO(Venta venta) {
        this.id = venta.getId();
        this.fechaDeVenta = venta.getFecha();
        this.ticket = venta.getNumeroVenta();
        this.total = venta.getTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaDeVenta() {
        return fechaDeVenta;
    }

    public void setFechaDeVenta(LocalDate fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
