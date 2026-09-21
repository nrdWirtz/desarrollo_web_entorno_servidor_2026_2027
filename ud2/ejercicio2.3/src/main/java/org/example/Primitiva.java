package org.example;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Primitiva {
    private Set<Integer> numerosPremiados = new TreeSet<>();

    public Primitiva() {
        Random random = new Random();
        while (this.numerosPremiados.size() < 6) {
            this.numerosPremiados.add(random.nextInt(49) + 1);
        }
    }

    public Set<Integer> getNumerosPremiados() {
        return this.numerosPremiados;
    }

    public int getCantidadAciertos(Set<Integer> boleto) {
        int cantAciertos = 0;
        for (Integer numero : boleto) {
            if (numerosPremiados.contains(numero))
                cantAciertos++;
        }
        return cantAciertos;
    }
}
