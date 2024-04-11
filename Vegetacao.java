import java.awt.Color;

public class Vegetacao implements ElementoMapa {
    private Color cor;
    private String simbolo;

    public Vegetacao(String simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }
    
    @Override
    public String getSimbolo() {
        return simbolo;
    }

    @Override
    public Color getCor() {
        return cor;
    }

    @Override
    public boolean podeSerAtravessado() {
        return true;
    }

    @Override
    public boolean podeInteragir() {
        return false;
    }

    @Override
    public String interage() {
        return null;
    }
}