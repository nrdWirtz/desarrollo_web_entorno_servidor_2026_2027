package org.dwes;

import java.time.LocalDate;

public class Termostato extends Dispositivo {

    private static final int TEMPERATURA_MINIMA = 15;
    private static final int TEMPERATURA_MAXIMA = 80;
    private static final int TEMPERATURA_INICIAL = 20;

    private int temperatura;
    private LocalDate fechaUltimaRevision;

    public Termostato() {
        temperatura = TEMPERATURA_INICIAL;
        fechaUltimaRevision = null;
    }

    @Override
    public boolean subir() {
        if (temperatura < TEMPERATURA_MAXIMA) {
            temperatura++;
            return true;
        }
        return false;
    }

    @Override
    public boolean bajar() {
        if (temperatura > TEMPERATURA_MINIMA) {
            temperatura--;
            return true;
        }

        return false;
    }

    @Override
    public void reset() {
        temperatura = TEMPERATURA_INICIAL;
    }

    @Override
    public String verEstado() {
        String revision;
        if (fechaUltimaRevision == null) {
            revision = "sin revisar";
        } else {
            revision = fechaUltimaRevision.toString();
        }

        return "Termostato - Temperatura: " + temperatura + " ºC - Última revisión: " + revision;
    }

    public void revisar() {
        fechaUltimaRevision = LocalDate.now();
    }
}