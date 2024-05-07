package Main;
import Entidades.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        final int NUM_HOSPEDES = 50;

        Hotel hotel = new Hotel();

        // Criando hóspedes
        for (int i = 0; i < NUM_HOSPEDES; i++) {
            new Hospede(i + 1, hotel).start();
        }
    }
}
