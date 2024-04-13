import java.awt.Color;

public class Inimigo implements ElementoMapa {
    private Color cor;
    private String simbolo;
    private int vida;
    private int x;
    private int y;

    public Inimigo(String simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
        this.vida = 1;
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
        return "";
    }
    
    public int getVida() {
        return vida;
    }

    public void reduzVidaInimigo(){
        vida -= 1;
    }

    @Override
    public int getQuantidadeVida() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQuantidadeVida'");
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
