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

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getX'");
    }

    @Override
    public int getY() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getY'");
    }

    @Override
    public void setX(int x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setX'");
    }

    @Override
    public void setY(int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setY'");
    }

    @Override
    public int getQuantidadeVida() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQuantidadeVida'");
    }

    @Override
    public void reduzVidaInimigo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reduzVidaInimigo'");
    }

    @Override
    public boolean getStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    }

    @Override
    public int getVida() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVida'");
    }

    @Override
    public void piscar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'piscar'");
    }
}