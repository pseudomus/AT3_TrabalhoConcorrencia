package Entidades;

public class Chave {
    private Quarto quarto;
    private int idChave;

    public Chave(Quarto quarto){
        this.quarto = quarto;
        this.idChave = quarto.getNumero();
    }

    public int getIdChave() {
        return idChave;
    }

    public Quarto getQuarto() {
        return quarto;
    }
}
