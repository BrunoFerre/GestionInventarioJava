/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.brunoferre.gestioninventario.vista;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author bruno
 */
public class FrmOpciones extends javax.swing.JFrame {

    public FrmOpciones() {
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - 1);// Posición en la derecha
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        this.setResizable(false);
        this.setEnabled(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        btnFaltantes = new javax.swing.JButton();
        btnGestionPr = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ACCESOS RAPIDOS");
        setEnabled(false);
        setUndecorated(true);

        jButton1.setBackground(new java.awt.Color(0, 109, 208));
        jButton1.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("MI CUENTA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnVenta.setBackground(new java.awt.Color(0, 109, 208));
        btnVenta.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        btnVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnVenta.setText("NUEVA VENTA");
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });

        btnFaltantes.setBackground(new java.awt.Color(0, 109, 208));
        btnFaltantes.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        btnFaltantes.setForeground(new java.awt.Color(255, 255, 255));
        btnFaltantes.setText("PRODUCTOS FALTANTES");
        btnFaltantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaltantesActionPerformed(evt);
            }
        });

        btnGestionPr.setBackground(new java.awt.Color(0, 109, 208));
        btnGestionPr.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        btnGestionPr.setForeground(new java.awt.Color(255, 255, 255));
        btnGestionPr.setText("GESTION DE PRODUCTOS");
        btnGestionPr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionPrActionPerformed(evt);
            }
        });

        btnHistorial.setBackground(new java.awt.Color(0, 109, 208));
        btnHistorial.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        btnHistorial.setForeground(new java.awt.Color(255, 255, 255));
        btnHistorial.setText("HISTORIAL DE VENTAS");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });

        btnCerrar.setBackground(new java.awt.Color(0, 109, 208));
        btnCerrar.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setText("CERRAR SESION");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(0, 109, 208));
        btnSalir.setFont(new java.awt.Font("Ubuntu Mono", 1, 12)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("CERRAR SISTEMA");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGestionPr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFaltantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnGestionPr, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnFaltantes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        // TODO add your handling code here:
        FrmVentas frmVentas = new FrmVentas();
        frmVentas.setVisible(true);
        btnVenta.disable();
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnGestionPrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionPrActionPerformed
        // TODO add your handling code here:
        FrmProductos frmProductos = new FrmProductos();
        frmProductos.setVisible(true);
        btnGestionPr.disable();
    }//GEN-LAST:event_btnGestionPrActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        dispose();
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MiCuenta cuenta = new MiCuenta();
        cuenta.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnFaltantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaltantesActionPerformed
        // TODO add your handling code here:
        FrmProductosFaltantes faltantes = new FrmProductosFaltantes();
        faltantes.setVisible(true);
    }//GEN-LAST:event_btnFaltantesActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        // TODO add your handling code here:
        FrmHistorial historial = new FrmHistorial();
        historial.setVisible(true);
    }//GEN-LAST:event_btnHistorialActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnFaltantes;
    private javax.swing.JButton btnGestionPr;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVenta;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
