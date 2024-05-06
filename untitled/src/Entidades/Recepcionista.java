package Entidades;

import java.util.Random;

public class Recepcionista extends Thread {
    private final int id;
    private boolean isFree;
    private final Hotel hotel;

    public Recepcionista(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isFree = true;
    }

    public boolean getIsFree(){
        return this.isFree;
    }

    public void setIsFree(boolean isFree){
        this.isFree = isFree;
    }

    @Override
    public void run() {
        System.out.println("Recepcionista " + id + " pronto para receber hóspedes.");
        while (true) {
            try {
                hotel.receberHospede();
                Thread.sleep(new Random().nextInt(5000)); // Simulando tempo de atendimento
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}