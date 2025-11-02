package br.edu.ifsp.data;

import br.edu.ifsp.main.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CartaData {

    // variaveis
    private final String ARQUIVO_PATH = "src/main/java/br/edu/ifsp/data/carta.csv";
    private ArrayList<Carta> cartas = new ArrayList<>();

    // construtor padrao
    public CartaData(){
        carregarDados();
    }

    // metodos especificos para o CRUD os dados das Cartas
    public void carregarDados(){

        File arquivo = new File( ARQUIVO_PATH );

        try( Scanner leitura = new Scanner(arquivo) ){
            // pula o cabecalho
            if (leitura.hasNextLine()) {
                leitura.nextLine();
            }

            while ( leitura.hasNextLine() ){
                String linha = leitura.nextLine();
                Carta carta = converterLinhaCarta( linha );
                if( carta != null ){
                    cartas.add( carta );
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println( "Local de arquivo nao encontrado!" );
        }

    }

    public Carta converterLinhaCarta( String linha ){

        String[] campos = linha.split(",");

        try{
            String nome = campos[0];
            int nivel = Integer.parseInt(campos[1]);
            double custoElixir = Double.parseDouble(campos[2]);
            Tipo tipo = Tipo.valueOf( campos[3].toUpperCase() );
            Raridade raridade = Raridade.valueOf( campos[4].toUpperCase() );
            String caminhoImagem = campos[5];
            int dano = Integer.parseInt(campos[6]);
            int danoPorSegundo = Integer.parseInt(campos[7]);
            int vida = Integer.parseInt(campos[8]);
            Alvo alvo = Alvo.valueOf( campos[9].toUpperCase() );
            int alcance = Integer.parseInt(campos[10]);
            double velocidade = Double.parseDouble(campos[11]);
            double velocidadeDeImpacto = Double.parseDouble(campos[12]);

            return new Carta( nome, nivel, custoElixir, tipo, raridade, caminhoImagem, dano, danoPorSegundo, vida, alvo, alcance, velocidade, velocidadeDeImpacto );
        } catch (Exception e) {
            System.err.println( "Erro: " + e.getMessage() );
            return null;
        }

    }

    private void gravarDados(){

        try ( PrintWriter writer = new PrintWriter( new FileWriter(ARQUIVO_PATH) ) ) {

            writer.println("Nome,Nivel,CustoElixir,Tipo,Raridade,CaminhoImagem,Dano,DPS,Vida,Alvo,Alcance,Velocidade,VelocidadeImpacto");

            for (Carta carta : cartas) {
                writer.println(converterCartaParaLinha(carta));
            }

        } catch (IOException e) {
            System.err.println("Erro ao gravar dados: " + e.getMessage());
        }

    }

    private String converterCartaParaLinha(Carta carta) {

        return String.join(",",
                carta.getNome(),
                String.valueOf( carta.getNivel() ),
                String.valueOf( carta.getCustoElixir() ),
                carta.getTipo().name(),
                carta.getRaridade().name(),
                carta.getCaminhoImagem(),
                String.valueOf( carta.getDano() ),
                String.valueOf( carta.getDanoPorSegundo() ),
                String.valueOf( carta.getVida() ),
                String.valueOf( carta.getAlvo() ),
                String.valueOf( carta.getAlcance() ),
                String.valueOf( carta.getVelocidade() ),
                String.valueOf( carta.getVelocidadeDeImpacto() )
        );
    }

    public Carta buscarCartaPorNome( String nome ) {

        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        String nomeBusca = nome.trim().toLowerCase();
        for (Carta carta : cartas) {
            if (carta.getNome().toLowerCase().equals(nomeBusca)) {
                return carta;
            }
        }

        return null;

    }

    // metodo para criar a carta e salvar no arquivo
    public boolean criarCarta(Carta novaCarta) {

        if( cartas.contains(novaCarta) ) {
            System.out.println( "Carta já existe!" );
            return false;
        }

        cartas.add( novaCarta );
        gravarDados();
        return true;
    }

    // metodo para ler as cartas
    public ArrayList<Carta> lerCartas() {
         return new ArrayList<>(cartas);
    }

    // metodo para atualizar as cartas
    public boolean atualizarCarta( Carta cartaAtualizada ) {

        for( int i = 0; i < cartas.size(); i++ ) {
            if( cartas.get(i).equals(cartaAtualizada) ) {
                cartas.set( i, cartaAtualizada );
                gravarDados();
                return true;
            }
        }

        return false;

    }

    // metodo para excluir as cartas
    public boolean excluirCarta( Carta carta ) {

        if( cartas.contains(carta) ){
            cartas.remove(carta);
            gravarDados();
            return true;
        }

        return false;

    }

}
