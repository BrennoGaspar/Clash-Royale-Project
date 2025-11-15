package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.data.DeckData;
import br.edu.ifsp.main.*;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.util.ArrayList;

public class Cartas {

    private Stage stage;
    CartaData cartaDAO = new CartaData();
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

    public Cartas() {

        // CONTAINER
        VBox container = new VBox();

        // HEADER
        HBox header = new HBox( 300 );
        Button decks = new Button( "Decks" );
        decks.setOnAction( this::funcaoBotao );
        Button criarCarta = new Button( "Criar Carta" );
        Button colecao = new Button( "Colecao" );
        colecao.setOnAction( this::voltarParaColecao );

        header.getChildren().addAll( decks, criarCarta, colecao );
        header.setAlignment( Pos.TOP_CENTER );

        // BODY
        VBox body = new VBox();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(10);

        grid.add(new Label("Nome:"), 0, 0);
        nomeField = new TextField();
        grid.add(nomeField, 1, 0);

        grid.add(new Label("Nível:"), 0, 1);
        nivelField = new TextField();
        grid.add(nivelField, 1, 1);

        grid.add(new Label("Custo Elixir:"), 0, 2);
        custoElixirField = new TextField();
        grid.add(custoElixirField, 1, 2);

        grid.add(new Label("Tipo:"), 0, 3);
        tipoField = new ComboBox<>();
        tipoField.getItems().addAll("TROPA", "FEITIÇO", "CONSTRUCAO");
        tipoField.setPromptText("Selecione o Tipo");
        grid.add(tipoField, 1, 3);

        grid.add(new Label("Raridade:"), 0, 4);
        raridadeField = new ComboBox<>();
        raridadeField.getItems().addAll("COMUM", "RARA", "EPICA", "LENDARIA", "CAMPEAO");
        raridadeField.setPromptText("Selecione a Raridade");
        grid.add(raridadeField, 1, 4);

        grid.add(new Label("Caminho Imagem:"), 0, 5);
        caminhoImagemField = new TextField();
        caminhoImagemField.setPromptText("Caminho/URL da imagem");
        grid.add(caminhoImagemField, 1, 5);

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

        // --- 3. BOTÃO DE AÇÃO ---
        Button btnCadastrar = new Button("Cadastrar Carta");
        btnCadastrar.setOnAction( this::botaoAdicionar );
        btnCadastrar.setMaxWidth(Double.MAX_VALUE);

        // Colocado na coluna 1, na próxima linha
        grid.add( btnCadastrar, 1, 13 );

        body.getChildren().add( grid );

        // DECK
        HBox deck = new HBox( 50 );
        deck.setAlignment( Pos.CENTER );

        // FIM BODY
        body.getChildren().addAll( deck );
        body.setAlignment( Pos.CENTER );


        // FIM CONTAINER
        container.getChildren().addAll( header, body );

        Scene cena = new Scene( container, 1500, 700 );

        this.stage = new Stage();
        this.stage.setScene( cena );
        this.stage.setTitle( ":: Criar Nova Carta ::" );

    }

    public void exibir() {
        this.stage.show();
    }

    private void funcaoBotao(ActionEvent actionEvent) {

        Decks novaJanela = new Decks( cartaDAO );
        novaJanela.exibir();

        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();

    }

    private void voltarParaColecao( ActionEvent event ) {

        Colecao colecao = new Colecao();

        Stage novoStage = colecao.createStage( new Stage() );
        novoStage.show();

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    }

    private void botaoAdicionar( ActionEvent event ) {

        try{

            Carta c = new Carta( nomeField.getText(), Integer.parseInt(nivelField.getText()), Double.parseDouble(custoElixirField.getText()), Tipo.valueOf(tipoField.getValue()), Raridade.valueOf(raridadeField.getValue()), caminhoImagemField.getText(), Integer.parseInt(danoField.getText()), Integer.parseInt(danoPorSegundoField.getText()), Integer.parseInt(vidaField.getText()), Alvo.valueOf(alvoField.getValue()), Integer.parseInt(alcanceField.getText()), Double.parseDouble(velocidadeField.getText()), Double.parseDouble(velocidadeDeImpactoField.getText()) );
            cartaDAO.criarCarta( c );
            Alert alerta = new Alert( Alert.AlertType.CONFIRMATION );
            alerta.setTitle( "Carta criada com sucesso!" );
            alerta.showAndWait();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            Colecao colecao = new Colecao();
            Stage novoStage = colecao.createStage( new Stage() );
            novoStage.show();


        } catch ( Exception e ) {
            System.err.println( e );
        }


    }

}