package Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Quarto {
    private final int numero;
    private final int capacidade;
    private final Lock lock;
    private boolean disponivel;
    private boolean hasKey;
    private boolean beingCleaned;
    public  boolean estaLimpo;
    public Hospede hospede = null;
    public  Chave chave;


    public Quarto(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.lock = new ReentrantLock();
        this.disponivel = true;
        this.estaLimpo = true;
        this.beingCleaned = false;
        chave = new Chave(this);
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setBeingCleaned(boolean beingCleaned) {
        this.beingCleaned = beingCleaned;
    }

    public boolean getEstaLimpo() {
        return estaLimpo;
    }

    public void setEstaLimpo(boolean estaLimpo){
        this.estaLimpo = estaLimpo;
    }

    public boolean getBeingCleaned(){
        return this.beingCleaned;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setHaveKey(boolean haveKey) {
        this.hasKey = haveKey;
    }

     public boolean getHasKey(){
        return this.hasKey;
     }

     public Chave getChave() {
        return chave;
     }

    public void ocupar(Hospede hospede) {
        lock.lock();
        try {
            this.hospede = hospede;
            this.disponivel = false;
        } finally {
            lock.unlock();
        }
        hasKey = false;
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