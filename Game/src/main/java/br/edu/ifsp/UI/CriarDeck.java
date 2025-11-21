package br.edu.ifsp.UI;

import br.edu.ifsp.data.CartaData;
import br.edu.ifsp.data.DeckData;
import br.edu.ifsp.main.Carta;
import br.edu.ifsp.main.Deck;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CriarDeck {

    private ArrayList<Carta> cartasDoDeck = new ArrayList<>();
    private CartaData cartaDAO;
    private DeckData deckDAO;
    private Stage stage;

    private Label statusDeckLabel = new Label("DECK ATUAL (0/8)");
    private GridPane deckSlotsGrid = new GridPane();
    private VBox colecaoLista = new VBox(5);

    public CriarDeck(CartaData cartaDAO) {
        this.cartaDAO = cartaDAO;
        this.deckDAO = new DeckData(cartaDAO);

        BorderPane root = new BorderPane();
        root.setPrefSize(1200, 800);

        // --- 1. Lado Esquerdo: Coleção (Todas as Cartas Disponíveis) ---
        ScrollPane scrollPane = new ScrollPane(colecaoLista);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(300);

        ArrayList<Carta> todasCartas = cartaDAO.lerCartas();
        for (Carta c : todasCartas) {
            HBox item = new HBox(10);
            item.setAlignment(Pos.CENTER_LEFT);
            item.setPadding(new Insets(5));
            item.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");

            Button adicionarButton = new Button("Adicionar");
            adicionarButton.setOnAction(e -> adicionarCarta(c));

            Label nomeLabel = new Label(c.getNome());
            Label custoElixirLabel = new Label("(" + String.format("%.1f", c.getCustoElixir()) + ")");

            item.getChildren().addAll(nomeLabel, custoElixirLabel, new Label("|"), adicionarButton);
            colecaoLista.getChildren().add(item);
        }

        root.setLeft(scrollPane);
        BorderPane.setMargin(scrollPane, new Insets(10));

        // --- 2. Lado Direito/Central: Slots do Deck (As 8 Cartas) ---
        VBox deckArea = new VBox(15);
        deckArea.setAlignment(Pos.TOP_CENTER);
        deckArea.setPrefWidth(400);

        deckSlotsGrid.setAlignment(Pos.CENTER);
        deckSlotsGrid.setHgap(10);
        deckSlotsGrid.setVgap(10);

        deckArea.getChildren().addAll(statusDeckLabel, deckSlotsGrid);
        root.setCenter(deckArea);
        BorderPane.setMargin(deckArea, new Insets(10));

        // --- 3. Rodapé: Ação de Salvar ---
        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER);
        Button salvarDeck = new Button("Salvar Novo Deck");
        salvarDeck.setOnAction(this::salvarDeck);

        footer.getChildren().add(salvarDeck);
        root.setBottom(footer);
        BorderPane.setMargin(footer, new Insets(10));

        Scene scene = new Scene(root);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle("Criar Novo Deck");

        renderizarDeck();
    }

    // LÓGICA DE PERSISTÊNCIA E VALIDAÇÃO
    private void salvarDeck(ActionEvent event) {
        if (cartasDoDeck.size() != 8) {
            mostrarAlerta("Erro de Validação", "Um deck deve ter exatamente 8 cartas para ser salvo.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Deck novoDeck = new Deck(cartasDoDeck);

            deckDAO.criarDeck(novoDeck);

            mostrarAlerta("Sucesso!", "Deck salvo com sucesso! Contém 8 cartas.", Alert.AlertType.INFORMATION);

            this.stage.close();

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Erro de Validação", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Erro de I/O", "Houve um erro ao gravar o arquivo: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // LÓGICA DE RENDERIZAÇÃO E MANIPULAÇÃO DA LISTA
    private void renderizarDeck() {
        deckSlotsGrid.getChildren().clear();
        statusDeckLabel.setText("DECK ATUAL (" + cartasDoDeck.size() + "/8)");

        for (int i = 0; i < 8; i++) {
            VBox slot = new VBox(5);
            slot.setAlignment(Pos.CENTER);

            int row = i / 4;
            int col = i % 4;

            if (i < cartasDoDeck.size()) {
                Carta carta = cartasDoDeck.get(i);
                Label nomeCarta = new Label(carta.getNome());
                nomeCarta.setStyle("-fx-font-weight: bold;");

                Button remover = new Button("Remover");
                remover.setOnAction(e -> removerCarta(carta));

                slot.getChildren().addAll(nomeCarta, remover);
                slot.setStyle("-fx-border-color: green; -fx-padding: 5;");
            } else {
                slot.getChildren().add(new Label("Slot Vazio"));
                slot.setStyle("-fx-border-color: gray; -fx-padding: 5;");
            }

            deckSlotsGrid.add(slot, col, row);
        }
    }

    private void adicionarCarta(Carta carta) {
        if (cartasDoDeck.size() >= 8) {
            mostrarAlerta("Limite Atingido", "Um deck não pode ter mais de 8 cartas.", Alert.AlertType.WARNING);
            return;
        }

        if (cartasDoDeck.contains(carta)) {
            mostrarAlerta("Erro", "A carta " + carta.getNome() + " já está no deck.", Alert.AlertType.WARNING);
            return;
        }

        cartasDoDeck.add(carta);
        renderizarDeck();
    }

    private void removerCarta(Carta carta) {
        cartasDoDeck.remove(carta);
        renderizarDeck();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void exibir() {
        this.stage.show();
    }

    public Stage getStage() {
        return this.stage;
    }
}