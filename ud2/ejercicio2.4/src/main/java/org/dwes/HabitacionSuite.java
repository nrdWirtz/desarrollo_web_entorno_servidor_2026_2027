package org.dwes;

// @user dnr
public class HabitacionSuite extends Habitacion {

    private static final double PRECIO_DIA = 200.0;
    private static final double DESCUENTO = 0.20;
    private static final int DIAS_DESCUENTO = 7;

    public HabitacionSuite(int numero) {
        super(numero);
    }

    @Override
    public double calcularPrecio(int dias) {

        double precio = dias * PRECIO_DIA;

        if (dias >= DIAS_DESCUENTO) {
            precio = precio - precio * DESCUENTO;
        }

        return precio;
    }

    @Override
    public String getTipo() {
        return "Suite";
    }
}


