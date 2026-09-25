package org.dwes;

// @user dnr
public class HabitacionLowcost extends Habitacion{

    private static final double PRECIO_DIA = 50.0;
    public HabitacionLowcost(int numero) {
        super(numero);
    }

    @Override
    public double calcularPrecio(int dias) {

        return dias * PRECIO_DIA;
    }

    @Override
    public String getTipo() {

        return "Lowcost";
    }

}
