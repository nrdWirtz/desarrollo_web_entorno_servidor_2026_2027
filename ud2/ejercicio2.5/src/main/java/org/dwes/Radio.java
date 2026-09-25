package org.dwes;

public class Radio extends Dispositivo {

    private static final double DIAL_MINIMO = 88.0;
    private static final double DIAL_MAXIMO = 104.0;
    private static final double DIAL_INICIAL = 88.0;
    private static final double INCREMENTO = 0.1;

    private double dial;

    public Radio() {
        dial = DIAL_INICIAL;
    }

    @Override
    public boolean subir() {
        if (dial < DIAL_MAXIMO) {
            dial += INCREMENTO;
            return true;
        }
        return false;
    }

    @Override
    public boolean bajar() {
        if (dial > DIAL_MINIMO) {
            dial -= INCREMENTO;
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        dial = DIAL_INICIAL;
    }

    @Override
    public String verEstado() {
        return String.format("Radio - Dial: %.1f MHz", dial);
    }
}
