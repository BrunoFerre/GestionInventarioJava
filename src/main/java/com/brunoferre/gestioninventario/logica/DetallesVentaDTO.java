package com.brunoferre.gestioninventario.logica;

public class DetallesVentaDTO {

    private Long id;
    private String producto;
    private Long idProducto;
    private int stock;
    private Double subTotal;

    public DetallesVentaDTO() {
    }

    public DetallesVentaDTO(DetalleVenta dv) {
        this.id = dv.getId();
        this.idProducto = dv.getProducto().getId();
        this.producto = dv.getProducto().getNombre();
        this.stock = dv.getCantidad();
        this.subTotal = dv.getPrecio();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

}
