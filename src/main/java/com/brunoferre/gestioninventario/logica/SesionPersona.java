package com.brunoferre.gestioninventario.logica;

public class SesionPersona {

    private static Persona personaLogueada;

    public static void setPersona(Persona persona) {
        personaLogueada = persona;
    }

    public static Persona getPersona() {
        return personaLogueada;
    }
}
