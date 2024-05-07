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
        List<Hospede> hospedes = new ArrayList<>();
        Hotel hotel = new Hotel();

        // Criando hóspedes
        for (int i = 0; i < NUM_HOSPEDES; i++) {
            Hospede hospede = new Hospede(i + 1, hotel);
            hospedes.add(hospede);
        }

        for(Hospede hospede: hospedes){
            hospede.start();
        }

    }
}
