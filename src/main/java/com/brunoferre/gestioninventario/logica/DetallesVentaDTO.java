package com.brunoferre.gestioninventario.logica;

public class DetallesVentaDTO {

    private Long id;
    private Long idProducto;
    private String producto;
    private int cantidad;
    private Double precioUnitario;
    private Double subTotal;

    public DetallesVentaDTO() {
    }

    public DetallesVentaDTO(DetalleVenta dv) {
        this.id = dv.getId();
        this.idProducto = dv.getProducto().getId();
        this.producto = dv.getProducto().getNombre();
        this.cantidad = dv.getCantidad();
        this.precioUnitario = dv.getPrecioUnitario();
        this.subTotal = dv.getPrecioUnitario() * this.cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }



}
