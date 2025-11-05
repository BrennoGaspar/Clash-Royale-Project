package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.data.DeckData;
import br.edu.ifsp.main.Carta;
import br.edu.ifsp.main.Deck;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.util.ArrayList;

public class Decks {

    private Stage stage;

    DeckData deckDAO;
    private ArrayList<Deck> decks;
    private ArrayList<Carta> cartas;

    public Decks( CartaData cartaDAO ) {

        this.deckDAO = new DeckData( cartaDAO );

        // VERIFICAÇÃO ARRAY DECKS
        if (decks == null) {
            this.decks = deckDAO.lerDecks();
        }

        // CONTAINER
        VBox container = new VBox();

        // HEADER
        HBox header = new HBox( 600 );
        Button decks = new Button( "Decks" );
        Button colecao = new Button( "Colecao" );
        colecao.setOnAction( this::voltarParaColecao );

        header.getChildren().addAll( decks, colecao );
        header.setAlignment( Pos.TOP_CENTER );

        // BODY
        VBox body = new VBox();

        // DECK
        HBox deck = new HBox( 50 );
        if (this.decks != null) {
            for( Deck d : this.decks ){
                ArrayList<Carta> cartasDoDeck = d.getCartas();
                Label custoMedio = new Label( "DECK: Custo Médio: " + String.format( "%.1f", d.getCustoMedio() ) );
                custoMedio.setStyle( "-fx-font-weight: BOLD;" );
                deck.getChildren().add( custoMedio );
                if( cartasDoDeck != null ){
                    for( Carta c : cartasDoDeck ){
                        Label nome = new Label( c.getNome() );
                        deck.getChildren().addAll( nome );
                    }
                }
            }
        }
        deck.setAlignment( Pos.CENTER );

        // FIM BODY
        body.getChildren().addAll( deck );
        body.setAlignment( Pos.CENTER );


        // FIM CONTAINER
        container.getChildren().addAll( header, body );

        Scene cena = new Scene( container, 1500, 700 );

        this.stage = new Stage();
        this.stage.setScene( cena );
        this.stage.setTitle( ":: Decks ::" );

    }

    public void exibir() {
        this.stage.show();
    }

    private void voltarParaColecao( ActionEvent event ) {

            Colecao colecao = new Colecao();

            Stage novoStage = colecao.createStage( new Stage() );
            novoStage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    }
}