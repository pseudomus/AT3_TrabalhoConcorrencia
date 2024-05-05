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

}