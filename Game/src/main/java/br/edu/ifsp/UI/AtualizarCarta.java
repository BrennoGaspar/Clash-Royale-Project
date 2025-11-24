package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.main.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AtualizarCarta {

    private Stage stage;
    private Carta cartaOriginal;
    // VARIAVEIS "GLOBAIS"
    TextField nomeField;
    TextField nivelField;
    TextField custoElixirField;
    ComboBox<String> tipoField;
    ComboBox<String> raridadeField;
    TextField caminhoImagemField;
    TextField danoField;
    TextField danoPorSegundoField;
    TextField vidaField;
    ComboBox<String> alvoField;
    TextField alcanceField;
    TextField velocidadeField;
    TextField velocidadeDeImpactoField;

    public AtualizarCarta( Carta c, CartaData dataAntiga ) {
        this.cartaOriginal = c;

        // CONTAINER
        VBox container = new VBox();

        // BODY
        VBox body = new VBox();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(10);

        grid.add(new Label("Nome:"), 0, 0);
        nomeField = new TextField();
        nomeField.setEditable( false );
        grid.add(nomeField, 1, 0);

        grid.add(new Label("Nível:"), 0, 1);
        nivelField = new TextField();
        grid.add(nivelField, 1, 1);

        grid.add(new Label("Custo Elixir:"), 0, 2);
        custoElixirField = new TextField();
        grid.add(custoElixirField, 1, 2);

        grid.add(new Label("Tipo:"), 0, 3);
        tipoField = new ComboBox<>();
        tipoField.getItems().addAll("TROPA", "FEITICO", "CONSTRUCAO");
        tipoField.setPromptText("Selecione o Tipo");
        grid.add(tipoField, 1, 3);

        grid.add(new Label("Raridade:"), 0, 4);
        raridadeField = new ComboBox<>();
        raridadeField.getItems().addAll("COMUM", "RARA", "EPICA", "LENDARIA", "CAMPEAO");
        raridadeField.setPromptText("Selecione a Raridade");
        grid.add(raridadeField, 1, 4);

        grid.add(new Label("Arquivo Imagem:"), 0, 5);
        HBox fileSelectorBox = new HBox( 5 );
        caminhoImagemField = new TextField();
        caminhoImagemField.setPromptText("Caminho/URL da imagem");
        caminhoImagemField.setEditable( false );
        Button selectImage = new Button( "Selecionar" );
        selectImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecionar Imagem da Carta");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Arquivos de Imagem", "*.png", "*.jpg", "*.jpeg"),
                    new FileChooser.ExtensionFilter("Todos os Arquivos", "*.*")
            );

            File selectedFile = fileChooser.showOpenDialog(this.stage);

            if (selectedFile != null) {
                caminhoImagemField.setText(selectedFile.getAbsolutePath());
            }
        });
        fileSelectorBox.getChildren().addAll( caminhoImagemField, selectImage );
        grid.add( fileSelectorBox, 1, 5 );

        grid.add(new Label("Dano:"), 0, 6);
        danoField = new TextField();
        grid.add(danoField, 1, 6);

        grid.add(new Label("Dano/Segundo:"), 0, 7);
        danoPorSegundoField = new TextField();
        grid.add(danoPorSegundoField, 1, 7);

        grid.add(new Label("Vida:"), 0, 8);
        vidaField = new TextField();
        grid.add(vidaField, 1, 8);

        grid.add(new Label("Alvo:"), 0, 9);
        alvoField = new ComboBox<>();
        alvoField.getItems().addAll("TERRESTRE", "AEREO", "HIBRIDO", "CONSTRUCAO" );
        alvoField.setPromptText("Selecione o Alvo");
        grid.add(alvoField, 1, 9);

        grid.add(new Label("Alcance:"), 0, 10);
        alcanceField = new TextField();
        grid.add(alcanceField, 1, 10);

        grid.add(new Label("Velocidade:"), 0, 11);
        velocidadeField = new TextField();
        grid.add(velocidadeField, 1, 11);

        grid.add(new Label("Velocidade Impacto:"), 0, 12);
        velocidadeDeImpactoField = new TextField();
        grid.add(velocidadeDeImpactoField, 1, 12);

        this.preencherCampos( c );

        Button btnAtualizar = new Button("Atualizar Carta");
        btnAtualizar.setOnAction( e -> botaoEditar( c, dataAntiga ) );
        btnAtualizar.setMaxWidth(Double.MAX_VALUE);

        // Colocado na coluna 1, na próxima linha
        grid.add(btnAtualizar, 1, 13 );

        body.getChildren().add( grid );

        // FIM CONTAINER
        container.getChildren().addAll( body );

        Scene cena = new Scene( container, 400, 500 );

        this.stage = new Stage();
        this.stage.setScene( cena );
        this.stage.setTitle( ":: Atualizar Carta ::" );

    }

    public void exibir() {
        this.stage.show();
    }

    private void preencherCampos( Carta c ) {
        nomeField.setText(c.getNome());
        nivelField.setText(String.valueOf(c.getNivel()));
        custoElixirField.setText(String.format("%.2f", c.getCustoElixir()));
        danoField.setText(String.valueOf(c.getDano()));
        danoPorSegundoField.setText(String.valueOf(c.getDanoPorSegundo()));
        vidaField.setText(String.valueOf(c.getVida()));
        alcanceField.setText(String.valueOf(c.getAlcance()));
        velocidadeField.setText(String.valueOf(c.getVelocidade()));
        velocidadeDeImpactoField.setText(String.valueOf(c.getVelocidadeDeImpacto()));
        caminhoImagemField.setText(c.getCaminhoImagem());
        tipoField.setValue(c.getTipo().toString());
        raridadeField.setValue(c.getRaridade().toString());
        alvoField.setValue(c.getAlvo().toString());
    }

    private void botaoEditar( Carta c, CartaData dataAntiga ) {

        double custoElixirFieldValor = Double.parseDouble(custoElixirField.getText());

        if( custoElixirFieldValor > 9.0 || custoElixirFieldValor < 1.0 ){

            Alert alertaElixir = new Alert( Alert.AlertType.ERROR );
            alertaElixir.setContentText( "O custo de elixir deve ser menor ou igual a 9 OU maior ou igual a 1!" );
            alertaElixir.setTitle( "Erro no custo de elixir!" );
            custoElixirField.setText( "" );
            alertaElixir.showAndWait();

        } else {

            try {

                Carta cartaNova = new Carta(nomeField.getText(), Integer.parseInt(nivelField.getText()), Double.parseDouble(custoElixirField.getText()), Tipo.valueOf(tipoField.getValue()), Raridade.valueOf(raridadeField.getValue()), caminhoImagemField.getText(), Integer.parseInt(danoField.getText()), Integer.parseInt(danoPorSegundoField.getText()), Integer.parseInt(vidaField.getText()), Alvo.valueOf(alvoField.getValue()), Integer.parseInt(alcanceField.getText()), Double.parseDouble(velocidadeField.getText()), Double.parseDouble(velocidadeDeImpactoField.getText()));
                cartaNova.setNome(cartaOriginal.getNome());

                if (dataAntiga.atualizarCarta(cartaNova)) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setTitle("Carta atualizada com sucesso!");
                    alerta.setContentText("A carta " + cartaNova.getNome() + " foi atualizada com SUCESSO!");
                    this.stage.close();
                    alerta.showAndWait();
                } else {
                    System.err.println("DEBUG >> Atualização falhou!");
                }

            } catch (Exception e) {
                System.err.println(e);
            }

        }


    }

}