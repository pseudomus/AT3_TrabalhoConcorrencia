package Main;
import Entidades.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        final int NUM_QUARTOS = 10;
        final int NUM_HOSPEDES = 15;
        final int NUM_CAMAREIRAS = 10;
        final int NUM_RECEPCIONISTAS = 5;

        Hotel hotel = new Hotel(NUM_QUARTOS);

        // Criando recepcionistas
        for (int i = 0; i < NUM_RECEPCIONISTAS; i++) {
            new Recepcionista(i + 1, hotel).start();
        }

        // Criando hóspedes
        for (int i = 0; i < NUM_HOSPEDES; i++) {
            new Hospede(i + 1, hotel).start();
        }

        // Criando camareiras
        for (int i = 0; i < NUM_CAMAREIRAS; i++) {
            new Camareira(i + 1, hotel).start();
        }
    }
}
