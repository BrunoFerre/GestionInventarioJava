package com.brunoferre.gestioninventario.logica;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FacturaPDF {

    public static void crearFactura(Venta venta, List<DetallesVentaDTO> detalle) throws IOException {
        // **Ruta de la carpeta "facturas" en el Escritorio**
        String desktopPath = System.getProperty("user.home") + "/Desktop/facturas";
        Files.createDirectories(Paths.get(desktopPath)); // Crea la carpeta si no existe

        // **Ruta del archivo PDF**
        String destino = desktopPath + "/Factura_" + venta.getNumeroVenta() + ".pdf";
        File archivo = new File(destino);

        // **Si el archivo ya existe, solo se abre**
        if (archivo.exists()) {
            System.out.println("El archivo ya existe. Abriendo...");
        } else {
            crearFactura(destino, venta, detalle);
            System.out.println("Factura generada en: " + destino);
        }

        // **Abrir el PDF**
        Desktop.getDesktop().open(archivo);
    }

    public static void crearFactura(String destino, Venta venta, List<DetallesVentaDTO> detalle) throws FileNotFoundException, IOException {
        PdfWriter writer = new PdfWriter(destino);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 10, 20, 10);

        // **Obtener la imagen desde el classpath (src/images/avatar.jpg)**
        InputStream imageStream = FacturaPDF.class.getResourceAsStream("/images/avatar.jpg");
        System.out.println(imageStream);
        Image logo = null;

        if (imageStream != null) {
            byte[] imageBytes = imageStream.readAllBytes(); // Leer imagen
            ImageData imageData = ImageDataFactory.create(imageBytes);
            logo = new Image(imageData).setWidth(70).setHeight(70); // Tamaño del logo
        }

        // **Encabezado con Logo, Texto "Factura Nro:" y Número de Factura**
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2})).useAllAvailableWidth();

        // **1. Logo (Izquierda)**
        if (logo != null) {
            headerTable.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
        } else {
            headerTable.addCell(new Cell().add(new Paragraph("No Logo")).setBorder(Border.NO_BORDER));
        }
        // **2. Título "Factura Nro:" (Centro)**
        Paragraph titulo = new Paragraph("Factura Nro:")
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER);
        headerTable.addCell(new Cell().add(titulo).setBorder(Border.NO_BORDER));

        // **3. Número de Factura (Derecha)**
        Paragraph numeroFactura = new Paragraph("N-" + venta.getNumeroVenta())
                .setFontSize(14)
                .setTextAlignment(TextAlignment.RIGHT);
        headerTable.addCell(new Cell().add(numeroFactura).setBorder(Border.NO_BORDER)).setHeight(UnitValue.createPercentValue(30));

        document.add(headerTable);
        document.add(new Paragraph("\n")); // Espacio después del encabezado

        // **Fecha de Venta**
        String fecha = venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        document.add(new Paragraph("Fecha de venta: " + fecha)
                .setFontSize(12)
                .setMarginBottom(15));

        // **Tabla de Detalles**
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1, 2, 2})).setWidth(UnitValue.createPercentValue(90)).setHorizontalAlignment(HorizontalAlignment.CENTER); // Ajusta el número de columnas

        // Encabezados con fondo gris
        String[] headers = {"#", "Producto", "Cantidad", "Precio Unitario", "SubTotal"};
        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header).setBackgroundColor(new DeviceRgb(220, 220, 220)).setPadding(5)));
        }

        // **Agregar productos a la tabla**
        int contador = 1; // Contador para la columna "#"
        double total = 0;
        for (DetallesVentaDTO p : detalle) {
            Long idProducto = p.getIdProducto();
            String nombre = p.getProducto();
            int cantidad = p.getCantidad();
            double precio = p.getPrecioUnitario();
            Double subtotal = p.getSubTotal();
            total += subtotal; // Sumar el subtotal para el total
            // Agregar los detalles de cada producto
            table.addCell(new Cell().add(new Paragraph(String.valueOf(contador++)))); // Columna "#"
            table.addCell(new Cell().add(new Paragraph(nombre)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(cantidad))));
            table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", precio))));
            table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", subtotal))));
        }
        table.setMargin(30);
        document.add(table);

        // **Total alineado a la derecha**
        Table totalTable = new Table(UnitValue.createPercentArray(new float[]{1, 1})).useAllAvailableWidth();
        totalTable.addCell(new Cell().setBorder(Border.NO_BORDER)); // Celda vacía
        totalTable.addCell(new Cell().add(new Paragraph("Total: $" + String.format("%.2f", total))
                .setFontSize(14)
                .setTextAlignment(TextAlignment.RIGHT))
                .setBorder(Border.NO_BORDER));

        document.add(totalTable);
        document.close();
    }

}
