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
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FacturaPDF {

    //Generar PDF con jaspert report
    public void imprimir(Long idVenta) {
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gestion", "develop", "develop123");
            JasperReport reporte = null;
            String pathReport = "/Reportes/Factura.jasper";

            Map<String, Object> parametro = new HashMap<>();
            parametro.put("idVentaPrincipal", idVenta);
            // reporte = (JasperReport) JRLoader.loadObject(pathReport);
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(pathReport));
            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, con);
            JasperViewer ver = new JasperViewer(jprint);
            ver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            ver.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(FacturaPDF.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }
}
