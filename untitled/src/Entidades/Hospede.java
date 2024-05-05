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
}
