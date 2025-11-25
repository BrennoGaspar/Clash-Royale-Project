package br.edu.ifsp.UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TelaInicial extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        StackPane root = new StackPane();
        root.getStyleClass().add("background-principal");
        root.setPrefSize(1500, 700);

        Button playButton = new Button("");
        playButton.getStyleClass().add("button-battle");
        playButton.setOnAction(this::abrirMenuPrincipal);

        Label credits = new Label( "Desenvolvido por Brenno Gaspar e Cayke Chen | Alunos do 2° Semestre de BCC do IFSP-SBV" );
        credits.setAlignment( Pos.BOTTOM_CENTER );
        credits.setTranslateY( 330 );
        credits.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-padding: 5; -fx-effect: dropshadow( rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-font-weight: bold;");
        //mover botão
        playButton.setTranslateY(50);
        playButton.setTranslateX(-30);

        Image titleImage = new Image(getClass().getResourceAsStream("/images/title.png"));
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitWidth(450);
        titleImageView.setPreserveRatio(true);

        // Empurra para cima
        titleImageView.setTranslateY(-250);

        // Empurra para a direita
        titleImageView.setTranslateX(10);

        root.getChildren().addAll(playButton, titleImageView, credits);

        Scene scene = new Scene(root, 1500, 700);
        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());

        String cssPath = getClass().getResource("/css/estilos.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setScene(scene);
        stage.setTitle(":: Clash Royale - Início ::");
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