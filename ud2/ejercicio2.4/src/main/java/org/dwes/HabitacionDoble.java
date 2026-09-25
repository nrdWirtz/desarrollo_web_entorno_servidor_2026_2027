package org.dwes;

// @user dnr
public class HabitacionDoble extends Habitacion{

    private static final double PRECIO_DIA = 100.0;
    private static final double DESCUENTO = 0.10;
    private static final int DIAS_DESCUENTO = 5;

    public HabitacionDoble(int numero) {
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

        return "Doble";
    }
}

