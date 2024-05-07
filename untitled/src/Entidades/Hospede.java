package Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
        Camareira camareira = hotel.getCamareira();
        camareira.limparOQuarto();
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
        Recepcionista recepcionista = hotel.getRecepcionista();
        System.out.println("Hóspede " + id + " chegou ao hotel.");
        if (recepcionista != null && tentativasReclamacao > 0) {
            while (tentativasReclamacao > 0) {
                recepcionista.alocarQuarto(this);
                if (this.chave != null) {
                    try {
                        sairPassear(recepcionista);
                        Thread.sleep(4000);
                        if (!quarto.getBeingCleaned()) {
                            retornarProQuarto(recepcionista.devolverChave(quarto.getNumero()));
                        }
                        Thread.sleep(4000);
                        sairDoHotel(recepcionista);
                        break;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    tentativasReclamacao--;
                }
            }
            if (tentativasReclamacao == 0) {
            }
        }
    }
}