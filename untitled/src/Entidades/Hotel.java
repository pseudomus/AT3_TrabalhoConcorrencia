package Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    private final Quarto[] quartos;
    private final List<Hospede> filaEspera;
    private final List<Hospede> hospedesNoHotel;
    private final Lock lock;

    public Hotel(int numQuartos) {
        quartos = new Quarto[numQuartos];
        for (int i = 0; i < numQuartos; i++) {
            quartos[i] = new Quarto(i + 1, 4); // Capacidade de 4 hóspedes por quarto
        }
        filaEspera = new ArrayList<>();
        hospedesNoHotel = new ArrayList<>();
        lock = new ReentrantLock();
    }
}
