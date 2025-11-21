package br.edu.ifsp.data;

import br.edu.ifsp.main.Carta;
import br.edu.ifsp.main.Deck;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DeckData {

    private final String ARQUIVO_PATH = "src/main/java/br/edu/ifsp/data/deck.csv";
    private ArrayList<Deck> decks = new ArrayList<>();
    private CartaData cartaDAO;
    private int nextId = 1;

    public DeckData(CartaData cartaDAO) {
        this.cartaDAO = cartaDAO;
        carregarDados();
    }

    public void carregarDados(){
        File arquivo = new File( ARQUIVO_PATH );
        try( Scanner leitura = new Scanner( arquivo ) ){
            if( leitura.hasNextLine() ){
                leitura.nextLine();
            }
            int maxId = 0;
            while( leitura.hasNextLine() ){
                String linha = leitura.nextLine();
                Deck deck = converterLinhaDeck( linha );
                if( deck != null ){
                    decks.add( deck );
                    if (deck.getId() > maxId) {
                        maxId = deck.getId();
                    }
                }
            }
            nextId = maxId + 1;
        } catch (FileNotFoundException e) {
            System.out.println( "Arquivo nao encontrado!" );
        }
    }

    private Deck converterLinhaDeck( String linha ){
        String[] campos = linha.split( "," );

        try {
            int id = Integer.parseInt(campos[0].trim()); // Lê o ID
            ArrayList<Carta> cartasDoDeck = new ArrayList<>();

            if( this.cartaDAO != null ){
                for( int i = 1; i < campos.length; i++ ){ // Começa em 1 (após o ID)
                    String nomeCarta = campos[i].trim();
                    if (!nomeCarta.isEmpty()) {
                        Carta cartaEncontrada = this.cartaDAO.buscarCartaPorNome(nomeCarta);
                        if( cartaEncontrada != null ){
                            cartasDoDeck.add(cartaEncontrada);
                        } else {
                            System.err.println("Carta não encontrada no banco: " + nomeCarta);
                        }
                    }
                }
            } else {
                System.err.println("Impossível carregar decks: cartaDAO é null.");
                return null;
            }
            return new Deck( id, cartasDoDeck ); // Usa o construtor com ID

        } catch (Exception e) {
            System.err.println("Erro ao converter linha de deck: " + e.getMessage());
            return null;
        }
    }

    private void gravarDados() {
        try( PrintWriter writer = new PrintWriter( new FileWriter(ARQUIVO_PATH) ) ) {
            // novo cabeçalho, incluindo ID
            writer.println( "ID,Carta1.Nome,Carta2.Nome,Carta3.Nome,Carta4.Nome,Carta5.Nome,Carta6.Nome,Carta7.Nome,Carta8.Nome" );
            for( Deck deck : decks ) {
                writer.println( converterDeckParaLinha(deck) );
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados dos decks: " + e.getMessage());
        }
    }

    private String converterDeckParaLinha(Deck deck) {
        ArrayList<String> campos = new ArrayList<>();
        campos.add(String.valueOf(deck.getId())); // Adiciona o ID

        for( Carta carta : deck.getCartas() ) {
            campos.add( carta.getNome() );
        }
        while( campos.size() < 9 ){ // Agora precisa de 9 campos (ID + 8 cartas)
            campos.add( "" );
        }
        return String.join( ",", campos );
    }

    public ArrayList<Deck> lerDecks(){
        return new ArrayList<>(decks);
    }

    public boolean criarDeck( Deck novoDeck ){
        if (novoDeck.getId() == 0) {
            novoDeck.setId(nextId++); // Atribui ID e incrementa o contador
        }
        decks.add( novoDeck );
        gravarDados();
        return true;
    }

    public boolean atualizarDeck( Deck deckAtualizado ){
        for (int i = 0; i < decks.size(); i++) {
            //ele usa o ID para encontrar o deck
            if ( decks.get(i).equals(deckAtualizado) ) {
                decks.set( i, deckAtualizado );
                gravarDados();
                return true;
            }
        }
        return false;
    }

    public boolean excluirDeck( Deck deck ){
        if( decks.contains(deck) ){
            decks.remove(deck);
            gravarDados();
            return true;
        }
        return false;
    }
}