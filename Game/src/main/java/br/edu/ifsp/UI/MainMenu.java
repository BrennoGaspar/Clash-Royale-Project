package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private Stage primaryStage;
    private CartaData cartaDAO = new CartaData();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        VBox container = new VBox(50);
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("background-principal");

        HBox menuButtons = new HBox(50);
        menuButtons.setAlignment(Pos.CENTER);

        // --- Botões do Menu ---
        Button decksButton = new Button("Decks");
        decksButton.getStyleClass().add("button-menu");
        decksButton.setOnAction(this::DecksTela);

        Button criarCartaButton = new Button("Criar Carta");
        criarCartaButton.getStyleClass().add("button-menu");
        criarCartaButton.setOnAction(this::CriarCartaTela);

        Button colecaoButton = new Button("Coleção");
        colecaoButton.getStyleClass().add("button-menu");
        colecaoButton.setOnAction(this::ColecaoTela);

        menuButtons.getChildren().addAll(decksButton, criarCartaButton, colecaoButton);

        container.getChildren().add(menuButtons);

        Scene scene = new Scene(container, 1500, 700);
        String cssPath = getClass().getResource("/css/estilos.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setScene(scene);
        stage.setTitle("Clash Royale - Menu Principal");
        stage.show();
    }

    private void DecksTela(ActionEvent event) {
        Decks decksScreen = new Decks(this.cartaDAO);
        decksScreen.createStage(new Stage()).show();
        primaryStage.close();
    }

    private void CriarCartaTela(ActionEvent event) {
        Cartas cartasScreen = new Cartas();
        cartasScreen.exibir();
        primaryStage.close();
    }

    private void ColecaoTela(ActionEvent event) {
        Colecao colecaoScreen = new Colecao();
        colecaoScreen.createStage(new Stage()).show();
        primaryStage.close();
    }
}
