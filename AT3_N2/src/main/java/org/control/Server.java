package org.control;
import model.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final String ARQUIVO_JSON = "src/main/resources/livros.json";
    static List<Livro> livros;

    public static void main(String[] args) {
        carregarLivros();

        final int PORTA = 12344;

        try {
            ServerSocket servidorSocket = new ServerSocket(PORTA);
            System.out.println("Servidor pronto para receber conexões...");

            while (true) {
                Socket clienteSocket = servidorSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket);

                Thread clienteThread = new Thread(new Handler(clienteSocket));
                clienteThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static synchronized void carregarLivros() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(ARQUIVO_JSON);
        if (file.exists()) {
            try {
                JsonNode rootNode = mapper.readTree(file);
                JsonNode livrosNode = rootNode.path("livros");
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Livro.class);
                livros = mapper.convertValue(livrosNode, listType);
            } catch (IOException e) {
                e.printStackTrace();
                livros = new ArrayList<>();
            }
        } else {
            System.out.println("Arquivo JSON não encontrado, criando novo arquivo...");
            livros = new ArrayList<>();
            salvarLivros();
        }
    }

    static synchronized void salvarLivros() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(ARQUIVO_JSON), new Wrapper(livros));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
