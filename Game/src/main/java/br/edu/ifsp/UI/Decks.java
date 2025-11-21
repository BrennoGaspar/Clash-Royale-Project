package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.data.DeckData;
import br.edu.ifsp.main.Deck;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Optional;

public class Decks {

    private DeckData deckDAO;
    private CartaData cartaDAO;
    private Stage stage;

    // Construtor corrigido: Recebe a dependência CartaData
    public Decks(CartaData cartaDAO) {
        this.cartaDAO = cartaDAO;
    }

    public Stage createStage(Stage stage) {
        this.stage = stage;
        this.deckDAO = new DeckData(this.cartaDAO);

        BorderPane root = new BorderPane();
        root.setPrefSize(900, 600);

        // --- HEADER (Botões de Navegação) ---
        HBox header = new HBox( 30 );
        Button decks = new Button( "Decks" ); // Botão da tela atual

        // Botão de navegação para Criar Carta
        Button criarCarta = new Button( "Criar Carta" );
        criarCarta.setOnAction( this::funcaoBotaoCarta );

        // Botão de navegação para Coleção
        Button colecao = new Button( "Coleção" );
        colecao.setOnAction( this::funcaoBotaoColecao );

        header.getChildren().addAll( decks, criarCarta, colecao );
        header.setAlignment( Pos.TOP_CENTER );
        header.setPadding(new Insets(10));
        root.setTop(header);

        // --- Botão de Ação Principal (Criar Novo Deck) ---
        Button btnNovoDeck = new Button("Criar Novo Deck");
        btnNovoDeck.setOnAction(e -> abrirCriacaoDeck());

        VBox topActions = new VBox(10);
        topActions.setAlignment(Pos.CENTER_LEFT);
        topActions.setPadding(new Insets(10, 20, 0, 20));
        topActions.getChildren().add(btnNovoDeck);

        root.setTop(new VBox(header, topActions)); // Combina Header e botão principal

        // --- Lista de Decks (Centro) ---
        VBox listaDecks = new VBox(15);
        listaDecks.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(listaDecks);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        renderizarListaDecks(listaDecks);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gerenciamento de Decks");
        return stage;
    }

    public void exibir() {
        this.stage.show();
    }


    private void renderizarListaDecks(VBox listaDecks) {
        listaDecks.getChildren().clear();
        ArrayList<Deck> decks = deckDAO.lerDecks();

        if (decks.isEmpty()) {
            listaDecks.getChildren().add(new Label("Nenhum deck encontrado. Crie um novo!"));
            return;
        }

        for (Deck deck : decks) {
            HBox deckBox = new HBox(20);
            deckBox.setAlignment(Pos.CENTER_LEFT);
            deckBox.setPadding(new Insets(10));
            deckBox.setStyle("-fx-border-color: #333; -fx-border-width: 1px; -fx-background-color: #f4f4f4;");

            Label nomeDeck = new Label("Deck ID: " + deck.getId() + " - Média Elixir: " + String.format("%.1f", deck.getCustoMedio()));
            nomeDeck.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            HBox botoesAcao = new HBox(10);
            botoesAcao.setAlignment(Pos.CENTER_RIGHT);

            Button btnEditar = new Button("Editar");
            btnEditar.setOnAction(e -> abrirEdicaoDeck(deck));

            Button btnExcluir = new Button("Excluir");
            btnExcluir.setOnAction(e -> excluirDeck(deck, listaDecks));

            botoesAcao.getChildren().addAll(btnEditar, btnExcluir);

            HBox spacer = new HBox();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            deckBox.getChildren().addAll(nomeDeck, spacer, botoesAcao);
            listaDecks.getChildren().add(deckBox);
        }
    }

    private void abrirCriacaoDeck() {
        CriarDeck criarDeck = new CriarDeck(cartaDAO);
        criarDeck.exibir();

        criarDeck.getStage().setOnHidden(e -> renderizarListaDecks((VBox) ((ScrollPane) stage.getScene().getRoot()).getContent()));
    }

    private void funcaoBotaoCarta(ActionEvent actionEvent) {
        Cartas novaJanela = new Cartas();
        novaJanela.exibir();
        this.stage.close();
    }

    private void funcaoBotaoColecao(ActionEvent actionEvent) {
        Colecao colecao = new Colecao();
        Stage novoStage = colecao.createStage( new Stage() );
        novoStage.show();
        this.stage.close();
    }

    private void abrirEdicaoDeck(Deck deck) {
        mostrarAlerta("Funcionalidade Pendente", "A edição de decks será implementada no próximo passo.", AlertType.INFORMATION);
        // Próximo passo: Abrir CriarDeck(cartaDAO, deck)
    }

    private void excluirDeck(Deck deck, VBox listaDecks) {
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação de Exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir o Deck ID " + deck.getId() + "?");

        Optional<ButtonType> result = confirmacao.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                deckDAO.excluirDeck(deck);
                mostrarAlerta("Sucesso", "Deck excluído com sucesso!", AlertType.INFORMATION);

                renderizarListaDecks(listaDecks);
            } catch (Exception e) {
                mostrarAlerta("Erro de Exclusão", "Não foi possível excluir o deck: " + e.getMessage(), AlertType.ERROR);
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

    public Stage getStage() {
        return stage;
    }
}