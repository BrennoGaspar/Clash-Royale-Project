package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.main.Carta;
import br.edu.ifsp.main.Tipo;
import br.edu.ifsp.main.Raridade;
import br.edu.ifsp.main.Alvo;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import java.io.File;

public class Cartas {

    private Stage stage;
    CartaData cartaDAO = new CartaData();

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
        VBox container = new VBox( 20 );

        HBox header = new HBox( 300 );
        Button decks = new Button( "Decks" );
        decks.setOnAction( this::funcaoBotao );
        Button criarCarta = new Button( "Criar Carta" );
        Button colecao = new Button( "Colecao" );
        colecao.setOnAction( this::voltarParaColecao );

        header.getChildren().addAll( decks, criarCarta, colecao );
        header.setAlignment( Pos.TOP_CENTER );

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
                try {
                    // Define a pasta de destino dentro do seu projeto (ajuste o caminho se necessário!)
                    // Assumimos que o código está rodando no diretório-raiz do projeto.
                    String resourcePath = "src/main/resources/images/";

                    // 1. Gera um nome de arquivo único para evitar colisões
                    String originalFileName = selectedFile.getName();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String newFileName = UUID.randomUUID().toString() + extension;

                    // 2. Define o caminho de destino
                    Path targetPath = Paths.get(resourcePath + newFileName);

                    // 3. COPIA o arquivo físico para a pasta de resources
                    Files.copy(selectedFile.toPath(), targetPath);

                    // 4. Salva o CAMINHO RELATIVO (Classpath) no campo
                    String classpath = "/images/" + newFileName;
                    caminhoImagemField.setText(classpath);

                    mostrarAlerta("Sucesso", "Imagem copiada para resources com sucesso!", Alert.AlertType.INFORMATION);

                } catch (java.io.IOException ioException) {
                    mostrarAlerta("Erro de Arquivo", "Não foi possível copiar a imagem: " + ioException.getMessage(), Alert.AlertType.ERROR);
                }
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

        Button btnCadastrar = new Button("Cadastrar Carta");
        btnCadastrar.setOnAction( this::botaoAdicionar );
        btnCadastrar.setMaxWidth(Double.MAX_VALUE);

        grid.add( btnCadastrar, 1, 13 );

        body.getChildren().add( grid );

        HBox deck = new HBox( 50 );
        deck.setAlignment( Pos.CENTER );

        body.getChildren().addAll( deck );
        body.setAlignment( Pos.CENTER );

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
        Decks novaJanela = new Decks(cartaDAO);
        novaJanela.createStage(new Stage()).show();
        this.stage.close();
    }

    private void voltarParaColecao( ActionEvent event ) {
        Colecao colecao = new Colecao();
        Stage novoStage = colecao.createStage( new Stage() );
        novoStage.show();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    private void botaoAdicionar( ActionEvent event ) {

        double custoElixirFieldValor = Double.parseDouble(custoElixirField.getText());

        if( custoElixirFieldValor > 9.0 || custoElixirFieldValor < 1.0 ){

            Alert alertaElixir = new Alert( Alert.AlertType.ERROR );
            alertaElixir.setContentText( "O custo de elixir deve ser menor ou igual a 9 OU maior ou igual a 1!" );
            alertaElixir.setTitle( "Erro no custo de elixir!" );
            custoElixirField.setText( "" );
            alertaElixir.showAndWait();

        } else {

            try{
                Carta c = new Carta(
                        nomeField.getText(),
                        Integer.parseInt(nivelField.getText()),
                        Double.parseDouble(custoElixirField.getText()),
                        Tipo.valueOf(tipoField.getValue().toUpperCase()),
                        Raridade.valueOf(raridadeField.getValue().toUpperCase()),
                        caminhoImagemField.getText(),
                        Integer.parseInt(danoField.getText()),
                        Integer.parseInt(danoPorSegundoField.getText()),
                        Integer.parseInt(vidaField.getText()),
                        Alvo.valueOf(alvoField.getValue().toUpperCase()),
                        Integer.parseInt(alcanceField.getText()),
                        Double.parseDouble(velocidadeField.getText()),
                        Double.parseDouble(velocidadeDeImpactoField.getText())
                );

                boolean sucesso = cartaDAO.criarCarta( c );

                if (sucesso) {
                    Alert alerta = new Alert( Alert.AlertType.CONFIRMATION );
                    alerta.setTitle( "Carta criada com sucesso!" );
                    alerta.setContentText( "A carta " + c.getNome() + " foi criada com SUCESSO!" );
                    alerta.showAndWait();

                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                    Colecao colecao = new Colecao();
                    Stage novoStage = colecao.createStage( new Stage() );
                    novoStage.show();

                } else {
                    Alert alerta = new Alert( Alert.AlertType.ERROR );
                    alerta.setTitle( "Erro: Cadastro Duplicado" );
                    alerta.setContentText( "A carta '" + c.getNome() + "' já existe. Não é permitido cadastro duplicado." );
                    alerta.showAndWait();
                }
            } catch ( NumberFormatException e ) {
                Alert alerta = new Alert( Alert.AlertType.ERROR );
                alerta.setTitle( "Erro de Preenchimento" );
                alerta.setContentText( "Verifique se todos os campos numéricos (Nível, Dano, Elixir, etc.) contêm apenas números válidos e não estão vazios." );
                alerta.showAndWait();

            } catch ( NullPointerException | IllegalArgumentException e ) {
                Alert alerta = new Alert( Alert.AlertType.ERROR );
                alerta.setTitle( "Erro de Seleção/Valor" );
                alerta.setContentText( "Verifique se você selecionou um valor para todos os campos (Tipo, Raridade, Alvo) e se todos os campos estão preenchidos." );
                alerta.showAndWait();

            } catch ( Exception e ) {
                Alert alerta = new Alert( Alert.AlertType.ERROR );
                alerta.setTitle( "Erro Desconhecido" );
                alerta.setContentText( "Ocorreu um erro inesperado: " + e.getMessage() );
                alerta.showAndWait();
            }

        }

    }
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}