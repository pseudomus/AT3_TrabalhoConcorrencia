package Entidades;
import java.util.Random;

public class Camareira extends Thread{
    private final int id;
    private final Hotel hotel;

    public Camareira(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
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
