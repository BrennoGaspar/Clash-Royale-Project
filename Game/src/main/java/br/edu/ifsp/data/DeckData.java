package br.edu.ifsp.data;

import br.edu.ifsp.UI.Decks;
import br.edu.ifsp.main.Carta;
import br.edu.ifsp.main.Deck;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DeckData {

    // variaveis
    private final String ARQUIVO_PATH = "src/main/java/br/edu/ifsp/data/deck.csv";
    private ArrayList<Deck> decks = new ArrayList<>();
    private CartaData cartaDAO;

    // construtor nao padrao
    public DeckData(CartaData cartaDAO) {
        this.cartaDAO = cartaDAO;
        carregarDados();
    }

    // metodos especificos para o CRUD os dados dos Decks
    public void carregarDados(){

        File arquivo = new File( ARQUIVO_PATH );

        try( Scanner leitura = new Scanner( arquivo ) ){
            if( leitura.hasNextLine() ){
                leitura.nextLine();
            }

            while( leitura.hasNextLine() ){
                String linha = leitura.nextLine();
                Deck deck = converterLinhaDeck( linha );
                if( deck != null ){
                    decks.add( deck );
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println( "Arquivo nao encontrado!" );
        }

    }

    private Deck converterLinhaDeck( String linha ){

        String[] cartas = linha.split(",");

        ArrayList<Carta> cartasDoDeck = new ArrayList<>();

        if (cartas.length < 8) {
            System.err.println("Linha CSV de Deck inválida (campos insuficientes): " + linha);
            return null;
        }

        try{
            return new Deck( cartasDoDeck );
        } catch (Exception e) {
            System.err.println( "Erro ao criar deck: " + e.getMessage() );
            return null;
        }

    }

    private void gravarDados() {

        try( PrintWriter writer = new PrintWriter( new FileWriter(ARQUIVO_PATH) ) ) {

            writer.println( "Carta1.Nome,Carta2.Nome,Carta3.Nome,Carta4.Nome,Carta5.Nome,Carta6.Nome,Carta7.Nome,Carta8.Nome" );

            for( Deck deck : decks ) {
                writer.println( converterDeckParaLinha(deck) );
            }

        } catch (IOException e) {
            System.err.println("Erro ao gravar dados dos decks: " + e.getMessage());
        }

    }

    private String converterDeckParaLinha(Deck deck) {

        ArrayList<String> campos = new ArrayList<>();

        for( Carta carta : deck.getCartas() ) {
            campos.add( carta.getNome() );
        }

        while( campos.size() < 8 ){
            campos.add( "" );
        }

        return String.join( ",", campos );

    }

    // metodo para ler os decks
    public ArrayList<Deck> lerDecks(){
        return new ArrayList<>(decks);
    }

    // metodo para criar um novo deck
    public boolean criarDeck( Deck novoDeck ){

        decks.add( novoDeck );
        gravarDados();
        return true;

    }

    // metodo para atualizar o deck
    public boolean atualizarDeck( Deck deckAtualizado ){

        for (int i = 0; i < decks.size(); i++) {
            if ( decks.get(i).equals(deckAtualizado) ) {
                decks.set( i, deckAtualizado );
                gravarDados();
                return true;
            }
        }
        return false;

    }

    // metodo para excluir um deck
    public boolean excluirDeck( Deck deck ){

        if( decks.contains(deck) ){
            decks.remove(deck);
            gravarDados();
            return true;
        }

        return false;

    }

}
