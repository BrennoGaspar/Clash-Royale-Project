package br.edu.ifsp.UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TelaInicial extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        StackPane root = new StackPane();
        root.getStyleClass().add("background-principal");

        //Esta linha para garantir que o root preencha 100%
        root.setPrefSize(1500, 700); // Define um tamanho preferencial inicial para o StackPane
        // Isso é útil especialmente se o StackPane não tiver filhos que o "esticam"

        

        Button playButton = new Button("PLAY");
        // Aplique a classe CSS ao botão
        playButton.getStyleClass().add("play-button");
        playButton.setOnAction(this::abrirMenuPrincipal);

        root.getChildren().add(playButton);

        Scene scene = new Scene(root, 1500, 700);

        // --- Adicione estas linhas para que o root se ajuste ao tamanho da Scene ---
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

        String cssPath = getClass().getResource("/css/estilos.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setScene(scene);
        stage.setTitle("Clash Royale - Início");
        stage.show();
    }

    private void abrirMenuPrincipal(ActionEvent event) {
        MainMenuScreen mainMenu = new MainMenuScreen();
        mainMenu.start(new Stage());
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}