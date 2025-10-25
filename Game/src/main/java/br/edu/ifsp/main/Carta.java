package br.edu.ifsp.main;

import java.io.File;

public class Carta {

    // atributos específicos de uma carta
    private String nome;
    private int nivel;
    private double custoElixir;
    private Tipo tipo;
    private Raridade raridade;
    private File imagem;
    private int dano;
    private int danoPorSegundo;
    private int vida;
    private Alvo alvo;
    private int alcance;
    private String velocidade;
    private String velocidadeDeImpacto;

    // construtor nao padrao
    public Carta(String nome, int nivel, double custoElixir, Tipo tipo, Raridade raridade, File imagem, int dano, int danoPorSegundo, int vida, Alvo alvo, int alcance, String velocidade, String velocidadeDeImpacto) {
        this.nome = nome;
        this.nivel = nivel;
        this.custoElixir = custoElixir;
        this.tipo = tipo;
        this.raridade = raridade;
        this.imagem = imagem;
        this.dano = dano;
        this.danoPorSegundo = danoPorSegundo;
        this.vida = vida;
        this.alvo = alvo;
        this.alcance = alcance;
        this.velocidade = velocidade;
        this.velocidadeDeImpacto = velocidadeDeImpacto;
    }

    // metodos getters
    public String getNome() {
        return nome;
    }

    public int getNivel() {
        return nivel;
    }

    public double getCustoElixir() {
        return custoElixir;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public File getImagem() {
        return imagem;
    }

    public int getDano() {
        return dano;
    }

    public int getDanoPorSegundo() {
        return danoPorSegundo;
    }

    public int getVida() {
        return vida;
    }

    public Alvo getAlvo() {
        return alvo;
    }

    public int getAlcance() {
        return alcance;
    }

    public String getVelocidade() {
        return velocidade;
    }

    public String getVelocidadeDeImpacto() {
        return velocidadeDeImpacto;
    }

    // metodos setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNivel(int nivel) {
        if( nivel < 1 ){
            this.nivel = 1;
        } else if ( nivel > 15 ){
            this.nivel = 15;
        } else {
            this.nivel = nivel;
        }
    }

    public void setCustoElixir(double custoElixir) {
        if (custoElixir < 1.0) {
            this.custoElixir = 1.0;
        } else if (custoElixir > 9.0) {
            this.custoElixir = 9.0;
        } else {
            this.custoElixir = custoElixir;
        }
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setRaridade(Raridade raridade) {
        this.raridade = raridade;
    }

    public void setImagem(File imagem) {
        this.imagem = imagem;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public void setDanoPorSegundo(int danoPorSegundo) {
        this.danoPorSegundo = danoPorSegundo;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    public void setVelocidade(String velocidade) {
        this.velocidade = velocidade;
    }

    public void setVelocidadeDeImpacto(String velocidadeDeImpacto) {
        this.velocidadeDeImpacto = velocidadeDeImpacto;
    }

    // comparar o nome dos objetos
    @Override
    public boolean equals(Object obj) {
        Carta carta = (Carta)obj;
        return nome.equals(carta.nome);
    }
}

