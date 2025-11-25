package br.edu.ifsp.main;

import java.util.ArrayList;
import java.util.Objects;

public class Deck {

    private int id;
    private ArrayList<Carta> cartas;

    // Construtor para carregar do arquivo (com ID existente)
    public Deck(int id, ArrayList<Carta> cartas) {
        this.id = id;
        this.cartas = new ArrayList<>(cartas);
    }

    // Construtor para criar um novo deck (ID inicial 0)
    public Deck(ArrayList<Carta> cartas) {
        this(0, cartas);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public ArrayList<Carta> getCartas() {
        return this.cartas;
    }

    public double getCustoMedio() {
        double custo = 0.0;
        for( Carta c : cartas ){
            custo += c.getCustoElixir();
        }
        return custo / cartas.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        // A igualdade é definida apenas pelo ID
        return id == deck.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}