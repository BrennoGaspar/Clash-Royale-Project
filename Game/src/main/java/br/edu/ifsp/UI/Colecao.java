package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.main.Carta;
import javafx.application.Application;
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

public class Colecao extends Application {

    CartaData cartaDAO = new CartaData();
    private ArrayList<Carta> cartas;

    public Stage createStage(Stage stage) {

        // VERIFICAÇÃO ARRAY CARTAS
        if (cartas == null) {
            this.cartas = cartaDAO.lerCartas();
        }

        // CONTAINER
        VBox container = new VBox();

        // HEADER
        HBox header = new HBox( 300 );
        Button decks = new Button( "Decks" );
        decks.setOnAction( this::funcaoBotao );
        Button criarCarta = new Button( "Criar Carta" );
        criarCarta.setOnAction( this::funcaoBotaoCriarCarta );
        Button colecao = new Button( "Colecao" );

        header.getChildren().addAll( decks, criarCarta, colecao );
        header.setAlignment( Pos.TOP_CENTER );

        // BODY
        VBox body = new VBox();

        // CARD
        HBox card = new HBox();
        if (this.cartas != null) {
            for( Carta c : this.cartas ){
                Label nome = new Label( c.getNome() );
                nome.setStyle( "-fx-padding: 0 10 0 30;" );
                Label custoElixir = new Label( String.format( "%.1f", c.getCustoElixir() ) );
                card.getChildren().addAll( nome, custoElixir );
            }
        }
        card.setAlignment( Pos.CENTER );

        // FIM BODY
        body.getChildren().addAll( card );
        body.setAlignment( Pos.CENTER );

        // FIM CONTAINER
        container.getChildren().addAll( header, body );

        Scene cena = new Scene( container, 1500, 700 );

        stage.setScene( cena );
        stage.setTitle( ":: Colecao ::" );
        return stage;

    }

    @Override
    public void start(Stage stage) {
        createStage(stage).show();
    }

    private void funcaoBotao(ActionEvent actionEvent) {

        Decks novaJanela = new Decks( cartaDAO );
        novaJanela.exibir();

        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();

    }

    private void funcaoBotaoCriarCarta(ActionEvent actionEvent) {

        Cartas novaJanela = new Cartas(  );
        novaJanela.exibir();

        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();

    }

    public static void main(String[] args) {
        launch();
    }

}