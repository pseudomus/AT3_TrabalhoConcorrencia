package Entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    //
    private int NUM_CAMAREIRAS = 5;
    private int NUM_RECEPCIONISTAS = 5;
    private int NUM_QUARTOS = 10;

    //Atributos para controle da simulação
    private List<Quarto> quartos;
    private List<Camareira> camareiras;
    private List<Recepcionista> recepcionistas;
    private List<Chave> chaves;
    private final List<Hospede> filaEspera;
    private final List<Hospede> todosHospedesNoHotel;
    private final Lock lock;

    //Funções para inicializar a classe
    public Hotel() {
        quartos = new ArrayList<>();
        camareiras = new ArrayList<>();
        recepcionistas = new ArrayList<>( );
        filaEspera = new ArrayList<>();
        chaves = new ArrayList<>();
        todosHospedesNoHotel = new ArrayList<>();
        lock = new ReentrantLock();
        criarRecepcionistas();
        criarCamareiras();
        criarQuartos();
    }

    public void criarCamareiras(){
        for (int i = 0; i < NUM_CAMAREIRAS; i++) {
            camareiras.add(new Camareira(i + 1, this));
        }
        for(Camareira camareira: camareiras){
            camareira.start();
        }
    }

    public void criarRecepcionistas(){
        for (int i = 0; i < NUM_RECEPCIONISTAS; i++) {
            recepcionistas.add(new Recepcionista(i + 1, this));
        }
        for(Recepcionista recepcionista: recepcionistas){
            recepcionista.start();
        }
    }

    public void criarQuartos(){
        for (int i = 0; i < NUM_QUARTOS; i++) {
            quartos.add(new Quarto(i + 1, 4)); // Capacidade de 4 hóspedes por quarto
        }
    }


    //Getters and Setters
    public List<Camareira> getCamareiras() {
        return camareiras;
    }

    public List<Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }

    public List<Hospede> getFilaEspera() {
        return filaEspera;
    }

    public List<Chave> getChaves() {
        return chaves;
    }

    //Metodos do hotel

    public Camareira getCamareira(){
        lock.lock();
        try{
            for(Camareira camareira: camareiras){
                if(!camareira.getisCleaning()){
                    return camareira;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public Recepcionista getRecepcionista(){
        lock.lock();
        try {
            for (Recepcionista recepcionista : recepcionistas) {
                if (recepcionista.getIsFree()) {
                    return recepcionista;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public Quarto getQuartoLivre(){
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (quarto.isDisponivel()) {
                    return quarto;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public Quarto getQuartoSujo(){
        lock.lock();
        try {
            for (Quarto quarto : quartos) {
                if (quarto.getHasKey() && !quarto.getEstaLimpo()){
                    quarto.setEstaLimpo(true);
                    quarto.setHaveKey(false);
                    return quarto;
                }
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public void adicionarEspera(Hospede hospede){
        if(!this.filaEspera.contains(hospede)) {
            System.out.println("Não há quartos disponíveis. O hóspede " + hospede.getId() + " está na fila de espera.");
            this.filaEspera.add(hospede);
        }
    }

    public void removerHospede(Hospede hospede){
        this.todosHospedesNoHotel.remove(hospede);
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
                todosHospedesNoHotel.add(hospede);
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
                if (quarto.hospede == hospede && todosHospedesNoHotel.contains(hospede)) {
                    quarto.desocupar();
                    quarto.setEstaLimpo(false);
                    todosHospedesNoHotel.remove(hospede);
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
                if (quarto.isDisponivel() && quarto.getEstaLimpo()) {
                    // Simula tempo para limpeza
                    Thread.sleep(new Random().nextInt(5000));
                    System.out.println("Camareira está limpando o quarto " + quarto.getNumero());
                    quarto.setEstaLimpo(true);
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
                    if (quarto.isDisponivel() && quarto.getEstaLimpo()) {
                        quartoDisponivel = quarto;
                        break;
                    }
                }
                if (quartoDisponivel != null) {
                    quartoDisponivel.ocupar(filaEspera.get(0));
                    todosHospedesNoHotel.add(filaEspera.get(0));
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
