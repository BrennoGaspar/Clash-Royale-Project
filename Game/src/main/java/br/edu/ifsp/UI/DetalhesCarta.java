package br.edu.ifsp.UI;

import br.edu.ifsp.main.Carta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DetalhesCarta {

    private Stage stage;
    private Carta carta;

    public DetalhesCarta(Carta carta) {
        this.carta = carta;
        this.stage = new Stage();
        this.stage.setTitle( "Detalhes da Carta: " + carta.getNome() );

        // Monta a UI
        VBox container = new VBox(15);
        container.setPadding( new Insets(20) );
        container.setAlignment( Pos.TOP_CENTER );

        container.getChildren().add( new Label("INFORMAÇÕES COMPLETAS") );

        HBox imageContainer = new HBox();
        String caminho = carta.getCaminhoImagem();
        try{
            Image imagemCarta = new Image( new FileInputStream(caminho) );
            ImageView viewImagem = new ImageView(imagemCarta);
            viewImagem.setFitHeight( 50 );
            viewImagem.setFitWidth( 50 );
            imageContainer.getChildren().addAll( viewImagem );
        } catch ( FileNotFoundException e ){
            System.err.println( "Erro >> Arquivo de imagem nao encontrado em: " + caminho );
            imageContainer.getChildren().add( new Label( "Foto não encontrada!" ) );
        }

        GridPane grid = new GridPane();
        grid.setHgap( 10 );
        grid.setVgap( 5 );

        int row = 0;

        grid.add( new Label("Nome:"), 0, row );
        grid.add( new Label(carta.getNome()), 1, row++ );

        grid.add( new Label("Nível:"), 0, row );
        grid.add( new Label(String.valueOf(carta.getNivel())), 1, row++ );

        grid.add( new Label("Custo de Elixir:"), 0, row );
        grid.add( new Label(String.format("%.1f", carta.getCustoElixir())), 1, row++ );

        grid.add( new Label("Tipo:"), 0, row );
        grid.add( new Label(carta.getTipo().toString()), 1, row++ );

        grid.add( new Label("Raridade:"), 0, row );
        grid.add( new Label(carta.getRaridade().toString()), 1, row++ );

        grid.add( new Label("Dano:"), 0, row );
        grid.add( new Label(String.valueOf(carta.getDano())), 1, row++ );

        grid.add( new Label("Dano por Segundo:"), 0, row );
        grid.add( new Label(String.valueOf(carta.getDanoPorSegundo())), 1, row++ );

        grid.add( new Label("Vida:"), 0, row );
        grid.add( new Label(String.valueOf(carta.getVida())), 1, row++ );

        grid.add( new Label("Alvo:"), 0, row );
        grid.add( new Label(carta.getAlvo().toString()), 1, row++ );

        grid.add( new Label("Alcance:"), 0, row );
        grid.add( new Label(String.valueOf(carta.getAlcance())), 1, row++ );

        grid.add( new Label("Velocidade:"), 0, row );
        grid.add( new Label(String.valueOf(carta.getVelocidade())), 1, row++ );

        grid.add( new Label("Velocidade de Impacto:"), 0, row );
        grid.add( new Label(String.valueOf(carta.getVelocidadeDeImpacto())), 1, row );

        container.getChildren().addAll( imageContainer, grid );

        Scene cena = new Scene( container, 400, 500 );
        this.stage.setScene( cena );

    }

    public void exibir() {
        this.stage.show();
    }
}