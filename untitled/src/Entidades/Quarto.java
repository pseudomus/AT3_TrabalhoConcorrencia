package Entidades;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quarto {
    private final int numero;
    private final int capacidade;
    private final Lock lock;
    private boolean disponivel;
    public static boolean estaLimpo;
    public Hospede hospede = null;
    public Quarto(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.lock = new ReentrantLock();
        this.disponivel = true;
        Quarto.estaLimpo = true;

    }

    public int getNumero() {
        return numero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void ocupar(Hospede hospede) {
        lock.lock();
        try {
            this.hospede = hospede;
            this.disponivel = false;
        } finally {
            lock.unlock();
        }
    }

    public void desocupar() {
        lock.lock();
        try {
            this.hospede = null;
            this.disponivel = true;
        } finally {
            lock.unlock();
        }
    }
}