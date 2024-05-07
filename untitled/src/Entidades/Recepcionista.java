package Entidades;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Recepcionista extends Thread {
    private final int id;
    private boolean isFree;
    private Lock lock;
    private final Hotel hotel;

    public Recepcionista(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isFree = true;
        this.lock = new ReentrantLock();
    }

    public boolean getIsFree(){
        return this.isFree;
    }

    public void setIsFree(boolean isFree){
        this.isFree = isFree;
    }

    //Metodos recepcionistas

    public void alocarQuarto(Hospede hospede){
        lock.lock();
        try{
            if(hospede.getTentativas() > 0) {
                Quarto quarto = hotel.getQuartoLivre();
                if (quarto != null) {
                    System.out.println("Hospede" + hospede.getId() + "colocado no quarto" + quarto.getNumero());
                    quarto.ocupar(hospede);
                    hospede.setChave(quarto.getChave());
                    hospede.setQuarto(quarto);
                } else {
                    hospede.decrementarTentativas();
                    hotel.adicionarEspera(hospede);
                    System.out.println("não tem quartos livres no hotel");
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public void chamarEspera(){
        try{
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                Hospede hospede = hotel.getFilaEspera().get(0);
                if(hospede != null) {
                    hotel.getFilaEspera().remove(0);
                    alocarQuarto(hospede);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    
    public void adicionarChave(Chave chave){
        hotel.getChaves().add(chave);
    }

    public Chave devolverChave(int numeroQuarto){
        List<Chave> chaves = hotel.getChaves();
        for(Chave chave: chaves){
            if(chave.getIdChave() == numeroQuarto){
                chaves.remove(chave);
                return chave;
            }
        }
        return null;
    }

    @Override
    public void run() {
        System.out.println("Recepcionista " + id + " pronto para receber hóspedes.");
        while (true) {
            try {
                if(!hotel.getFilaEspera().isEmpty()) {
                    this.chamarEspera();
                    Thread.sleep(new Random().nextInt(5000)); // Simulando tempo de atendimento
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}