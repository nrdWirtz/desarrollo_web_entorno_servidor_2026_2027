package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner;
    static Primitiva primitiva;
    public static void main(String[] args) {
        primitiva = new Primitiva();
        scanner = new Scanner(System.in);
        Set<Integer> boleto = crearBoleto();
        System.out.println("Premiados: "+ primitiva.getNumerosPremiados());
        System.out.println("Boleto: "+ boleto);
        System.out.println("Cantidad de aciertos:" + primitiva.getCantidadAciertos(boleto));

    }



    public static Set<Integer> crearBoleto (){
        Set<Integer> boleto = new TreeSet<>();
        while  (boleto.size()<6){
            try{
                System.out.printf("Introduce núm (%d)%n",boleto.size()+1);
                int num = Integer.parseInt(scanner.nextLine());
                if (num<0  || num > 49)
                    showError(2);
                else
                if (boleto.contains(num))
                    showError(3);
                else boleto.add(num);
            }
            catch (Exception exception) {showError(1);}

        }
        return boleto;
    }

    public static void showError (int codError) {
        switch (codError){
            case 1 -> System.out.println("Error: Tipo de datos incorrecto");
            case 2 -> System.out.println("Error: El número debe estar comprendido entre 1 y 49");
            case 3 -> System.out.println("Error: Número repetido");
        }
    }
}
