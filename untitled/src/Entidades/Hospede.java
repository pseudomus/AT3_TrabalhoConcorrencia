package Entidades;

import javax.swing.*;
import java.util.Random;

public class Hospede extends Thread {
    private final int id;
    private final Hotel hotel;
    private boolean hospedado = false;

    public Hospede(int id, Hotel hotel) {
        this.id = id;
        this.hotel = hotel;
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
