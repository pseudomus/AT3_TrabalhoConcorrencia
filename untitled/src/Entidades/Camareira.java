package Entidades;
import java.util.Random;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Camareira extends Thread{
    private final int id;
    private boolean isCleaning;
    private final Hotel hotel;
    private boolean isAvailable = true;
    private Quarto beingCleaned;
    private Lock lock;

    public Camareira(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isCleaning = false;
        this.lock = new ReentrantLock();
    }

    public boolean getisCleaning(){
        return this.isCleaning;
    }

    public void setIsCleaning(boolean isCleaning){
        this.isCleaning = isCleaning;
    }

    public void setAvailable() {
        this.isAvailable = !isAvailable;
    }

    public void limparOQuarto() {
        lock.lock();
        setAvailable();
        this.beingCleaned = hotel.getQuartoSujo();
        try {
            if (beingCleaned != null) {
                beingCleaned.setBeingCleaned(true);
                System.out.println(getName() + ": O quarta começou a ser limpo" + beingCleaned.getNumero());
                Thread.sleep(2000);
                System.out.println("Quarto " + beingCleaned.getNumero() + " limpo.");
                beingCleaned.setEstaLimpo(false);
                setAvailable();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }


    @Override
    public void run() {
        System.out.println("Camareira " + id + " iniciou o turno.");
        while (true) {
            try {
                hotel.limparQuarto();
                Thread.sleep(new Random().nextInt(5000)); // Simulando tempo de limpeza
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
