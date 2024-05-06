package Entidades;
import java.util.Random;

public class Camareira extends Thread{
    private final int id;
    private boolean isCleaning;
    private final Hotel hotel;

    public Camareira(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
        this.isCleaning = false;
    }

    public boolean getisCleaning(){
        return this.isCleaning;
    }

    public void setIsCleaning(boolean isCleaning){
        this.isCleaning = isCleaning;
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
