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

    public void alocarQuarto(Hospede hospede) throws InterruptedException {
        lock.lock();
        try {
            Quarto quartoDisponivel = null;
            for (Quarto quarto : quartos) {
                if (quarto.isDisponivel()) {
                    quartoDisponivel = quarto;
                    break;
                }
            }
            if (quartoDisponivel == null) {
                System.out.println("Não há quartos disponíveis. O hóspede " + hospede.getId() + " está na fila de espera.");
                filaEspera.add(hospede); // Adiciona à fila de espera (pode ser otimizado)
            } else {
                quartoDisponivel.ocupar(hospede);
                hospedesNoHotel.add(hospede);
//                hospede.setHospedado(true);
                System.out.println("Hóspede " + hospede.getId() + " alocado no quarto " + quartoDisponivel.getNumero());
            }
        } finally {
            lock.unlock();
        }
    }

    public void sair(Hospede hospede) {
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (quarto.hospede == hospede && hospedesNoHotel.contains(hospede)) {
                    quarto.desocupar();
                    Quarto.estaLimpo = false;
                    hospedesNoHotel.remove(hospede);
                    System.out.println("Hóspede " + hospede.getId() + " saiu do quarto " + quarto.getNumero());
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void limparQuarto() throws InterruptedException {
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (quarto.isDisponivel() && Quarto.estaLimpo == false) {
                    // Simula tempo para limpeza
                    Thread.sleep(new Random().nextInt(5000));
                    System.out.println("Camareira está limpando o quarto " + quarto.getNumero());
                    Quarto.estaLimpo = true;
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void receberHospede() throws InterruptedException {
        lock.lock();
        try {
            if (!filaEspera.isEmpty()) {
                Quarto quartoDisponivel = null;
                for (Quarto quarto : quartos) {
                    if (quarto.isDisponivel() && Quarto.estaLimpo == true) {
                        quartoDisponivel = quarto;
                        break;
                    }
                }
                if (quartoDisponivel != null) {
                    quartoDisponivel.ocupar(filaEspera.get(0));
                    hospedesNoHotel.add(filaEspera.get(0));
//                    filaEspera.get(0).setHospedado(true);
                    System.out.println("Hóspede " + filaEspera.get(0).getId() + " da fila de espera alocado no quarto " + quartoDisponivel.getNumero());
                    filaEspera.remove(0);
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
