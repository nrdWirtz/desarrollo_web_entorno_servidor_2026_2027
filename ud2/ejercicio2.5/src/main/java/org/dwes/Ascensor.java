package org.dwes;

public class Ascensor extends Dispositivo {

    private static final int PLANTA_MINIMA = 0;
    private static final int PLANTA_MAXIMA = 8;
    private static final int PLANTA_INICIAL = 0;

    private int planta;

    public Ascensor() {
        planta = PLANTA_INICIAL;
    }

    @Override
    public boolean subir() {
        if (planta < PLANTA_MAXIMA) {
            planta++;
            return true;
        }
        return false;
    }

    @Override
    public boolean bajar() {
        if (planta > PLANTA_MINIMA) {
            planta--;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        planta = PLANTA_INICIAL;
    }

    @Override
    public String verEstado() {
        return "Ascensor - Planta: " + planta;
    }
}