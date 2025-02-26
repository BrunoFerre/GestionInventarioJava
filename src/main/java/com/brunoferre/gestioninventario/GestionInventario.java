package com.brunoferre.gestioninventario;

import com.brunoferre.gestioninventario.logica.Controladora;
import com.brunoferre.gestioninventario.vista.FrmOpciones;
import com.brunoferre.gestioninventario.vista.Login;

public class GestionInventario {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Controladora control = new Controladora();
        FrmOpciones frmOpciones = new FrmOpciones();
        frmOpciones.setVisible(true);
//        Login login = new Login();
//        login.setVisible(true);
    }

}
