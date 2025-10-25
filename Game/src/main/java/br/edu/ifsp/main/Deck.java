package br.edu.ifsp.main;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Deck {

    // atributos específicos de um deck
    private ArrayList<Carta> cartas = new ArrayList<>();

    // construtor padrao (vazio)
    public Deck( ArrayList<Carta> cartas ){
        this.cartas = cartas;
    }

    // metodo getter
    public double getCustoMedio() {
        double custo = 0.0;
        for( Carta c : cartas ){
            custo += c.getCustoElixir();
        }
        if( cartas.isEmpty() ){
            return 0.0;
        } else {
            return ( custo / cartas.size() );
        }
    }

    public ArrayList<Carta> getCartas() {
        return this.cartas;
    }

}
