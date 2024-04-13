import java.awt.Color;

public class Inimigo implements ElementoMapa {
    private Color cor;
    private String simbolo;
    private int vida;
    private double x;
    private double y;

    public Inimigo(String simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
        this.vida = 1;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public String getSimbolo() {
        return simbolo;
    }

    public Color getCor() {
        return cor;
    }

    @Override
    public boolean podeSerAtravessado() {
        return false;
    }

    @Override
    public boolean podeInteragir() {
        return true;
    }

    @Override
    public String interage() {
        vida -= 1;
        return "Matou inimigo!";
    }

    @Override
    public int getQuantidadeVida() {
        return vida;
    }

    @Override
    public boolean getStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    }

    @Override
    public void piscar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'piscar'");
    }

    @Override
    public void setStatus(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }
}
