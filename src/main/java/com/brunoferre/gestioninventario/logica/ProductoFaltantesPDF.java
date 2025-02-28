package com.brunoferre.gestioninventario.logica;

import static com.brunoferre.gestioninventario.logica.FacturaPDF.crearFactura;
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
import java.time.LocalDate;
import java.util.List;

public class ProductoFaltantesPDF {

    Controladora control = new Controladora();
    List<Producto> lista = control.productosFaltantes();

    public void Abrir() throws IOException {
        String desktopPath = System.getProperty("user.home") + "/Desktop/documentos";
        Files.createDirectories(Paths.get(desktopPath)); // Crea la carpeta si no existe
        // **Ruta del archivo PDF**
        String fecha = LocalDate.now().toString();
        String destino = desktopPath + "/ProductosFaltantes_" + fecha + ".pdf";
        File archivo = new File(destino);

        // **Si el archivo ya existe, solo se abre**
        if (archivo.exists()) {
            Desktop.getDesktop().moveToTrash(archivo);
            generarPdf(destino, lista);
            System.out.println("El archivo ya existe. Abriendo...");
        } else {
            generarPdf(destino, lista);
            System.out.println("Factura generada en: " + destino);
        }
        // **Abrir el PDF**
        Desktop.getDesktop().open(archivo);
    }

    public static void generarPdf(String destino, List<Producto> lista) throws FileNotFoundException, IOException {

        PdfWriter writer = new PdfWriter(destino);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(20, 10, 20, 10);

        // **Obtener la imagen desde el classpath (src/images/avatar.jpg)**
        InputStream imageStream = ProductoFaltantesPDF.class.getResourceAsStream("/images/avatar.jpg");
        System.out.println(imageStream);
        Image logo = null;
        if (imageStream != null) {
            byte[] imageBytes = imageStream.readAllBytes(); // Leer imagen
            ImageData imageData = ImageDataFactory.create(imageBytes);
            logo = new Image(imageData).setWidth(70).setHeight(70); // Tamaño del logo
        }
        // **Encabezado del PDF**
        Table tableHeader = new Table(UnitValue.createPercentArray(new float[]{2, 4})).useAllAvailableWidth();

        // **1. Logo**
        if (logo != null) {
            tableHeader.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
        } else {
            tableHeader.addCell(new Cell().add(new Paragraph("No Logo")).setBorder(Border.NO_BORDER));
        }

        // **2. Título**
        Paragraph titulo = new Paragraph("PRODUCTOS FALTANTES")
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER);
        tableHeader.addCell(new Cell().add(titulo).setBorder(Border.NO_BORDER));
        document.add(tableHeader);
        document.add(new Paragraph("\n"));
        // **Tabla de detalles**
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 1, 2}))
                .setWidth(UnitValue.createPercentValue(90))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        // **Encabezados**
        String[] headers = {"#", "Producto", "Código", "Stock", "Precio"};
        for (String header : headers) {
            table.addHeaderCell(new Cell()
                    .add(new Paragraph(header))
                    .setBackgroundColor(new DeviceRgb(220, 220, 220))
                    .setPadding(5));
        }
        // **Agregar productos**
        int contador = 1;
        for (Producto pr : lista) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(contador++))));
            table.addCell(new Cell().add(new Paragraph(pr.getNombre())));
            table.addCell(new Cell().add(new Paragraph(pr.getCodigoProducto())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(pr.getStock()))));
            table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", pr.getPrecio()))));
        }
        table.setMargin(30);
        document.add(table);
        document.close();
    }
}
