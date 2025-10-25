package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.main.Alvo;
import br.edu.ifsp.main.Carta;
import br.edu.ifsp.main.Raridade;
import br.edu.ifsp.main.Tipo;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {


        HBox container = new HBox();

        Label txt = new Label( "TESTE" );

        container.getChildren().addAll( txt );
        container.setAlignment( Pos.CENTER );

        Scene cena = new Scene( container, 1820, 980 );


        stage.setScene( cena );
        stage.setTitle( ":: Clash Royale ::" );
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}