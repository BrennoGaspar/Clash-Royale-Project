package br.edu.ifsp.main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Deck {

    // atributos específicos de um deck
    private ArrayList<Carta> cartas = new ArrayList<>();

    // construtor padrao (vazio)
    public Deck( ArrayList<Carta> cartas ) {

        if( cartas == null ){
            this.cartas = new ArrayList<>();
            return;
        }

        if (cartas.size() > 8) {
            throw new IllegalArgumentException("Um deck não pode ter mais de 8 cartas.");
        }

        for( int i = 0; i < cartas.size(); i++ ){
            for( int j = i+1; j < cartas.size(); j++ ){
                if( cartas.get(i).equals( cartas.get(j) )){
                    throw new IllegalArgumentException("Um deck não pode conter cartas repetidas.");
                }
            }
        }

        this.cartas = new ArrayList<>(cartas);

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
