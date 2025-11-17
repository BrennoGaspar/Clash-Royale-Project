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

public class MainMenuScreen extends Application {

    private Stage primaryStage;
    // Precisamos do CartaData aqui para passar para a tela de Decks
    private CartaData cartaDAO = new CartaData();

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        VBox container = new VBox(50); // Painel principal com espaçamento
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().add("background-principal"); // Aplica o fundo

        HBox menuButtons = new HBox(50); // Painel para os botões
        menuButtons.setAlignment(Pos.CENTER);

        // --- Botão Decks ---
        Button decksButton = new Button("Decks");
        decksButton.getStyleClass().add("button-menu"); // Estilo do CSS
        decksButton.setOnAction(this::abrirTelaDecks);

        // --- Botão Criar Carta ---
        Button criarCartaButton = new Button("Criar Carta");
        criarCartaButton.getStyleClass().add("button-menu");
        criarCartaButton.setOnAction(this::abrirTelaCriarCarta);

        // --- Botão Coleção ---
        Button colecaoButton = new Button("Coleção");
        colecaoButton.getStyleClass().add("button-menu");
        colecaoButton.setOnAction(this::abrirTelaColecao);

        menuButtons.getChildren().addAll(decksButton, criarCartaButton, colecaoButton);
        container.getChildren().add(menuButtons);

        // --- Carrega a Cena e o CSS ---
        Scene scene = new Scene(container, 1500, 700);
        String cssPath = getClass().getResource("/css/estilos.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setScene(scene);
        stage.setTitle("Clash Royale - Menu Principal");
        stage.show();
    }

    // --- Métodos de Navegação ---

    private void abrirTelaDecks(ActionEvent event) {
        Decks decksScreen = new Decks(cartaDAO); // Passa o CartaData
        decksScreen.exibir();
        primaryStage.close(); // Fecha a tela de Menu
    }

    private void abrirTelaCriarCarta(ActionEvent event) {
        Cartas cartasScreen = new Cartas();
        cartasScreen.exibir();
        primaryStage.close(); // Fecha a tela de Menu
    }

    private void abrirTelaColecao(ActionEvent event) {
        Colecao colecaoScreen = new Colecao();
        // O .createStage da Colecao já cria e retorna um novo Stage
        colecaoScreen.createStage(new Stage()).show();
        primaryStage.close(); // Fecha a tela de Menu
    }
}