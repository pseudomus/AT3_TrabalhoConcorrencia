package Entidades;

import java.util.Random;

public class Hospede extends Thread {
    private final int id;
    private final Hotel hotel;
    private Quarto quarto;
    private boolean hospedado = false;
    private Thread thread;
    private Chave chave;
    private int tentativasReclamacao = 2;

    public Hospede(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.quarto = null;
        this.thread = new Thread(this);
    }

    public void setQuarto(Quarto quarto){
        this.quarto = quarto;
    }

    public void setChave(Chave chave) {
        this.chave = chave;
    }

    public int getTentativas() {
        return tentativasReclamacao++;
    }

    public void decrementarTentativas() {
        tentativasReclamacao--;
    }

    public Thread getThread() {
        return thread;
    }

    public void reclamar() {
        System.out.println(this.getId() + ": O Hotel não tem quartos disponíveis. Péssimo, não voltarei!");
        Thread.currentThread().interrupt();
    }

    public void entregarChaveRecepcao(Recepcionista recepcionista){
        recepcionista.adicionarChave(chave);
        this.chave = null;
        quarto.setHaveKey(false);
    }

    public void sairDoHotel(Recepcionista recepcionista) {
        entregarChaveRecepcao(recepcionista);
        hotel.removerHospede(this);
        System.out.println(this.getId() + " deixando o Hotel");
    }

    public void sairPassear(Recepcionista recepcionista) {
        entregarChaveRecepcao(recepcionista);
        System.out.println(this.getId() + " saiu para passear");
//        Camareira camareira = hotel.camareiraDisponivel();
//        camareira.limparQuarto();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void retornarProQuarto(Chave chave) {
        this.chave = chave;
        System.out.println(this.getId() + " voltando pro quarto");
    }

    @Override
    public void run() {
        System.out.println("Hóspede " + id + " chegou ao hotel.");
        try {
            hotel.alocarQuarto(this);
            Thread.sleep(new Random().nextInt(5000));
            hotel.sair(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}