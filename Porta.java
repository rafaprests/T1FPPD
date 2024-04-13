import java.awt.Color;

public class Porta implements ElementoMapa {
    private Color cor;
    private String simbolo;
    private int x;
    private int y;
    private boolean status;

    public Porta(String simbolo, Color cor) {
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
    public int getX(){
        return x;
    }

    @Override
    public int getY(){
        return y;
    }

    @Override
    public void setX(int x){
        this.x = x;
    }

    @Override
    public void setY(int y){
        this.y = y;
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
        this.simbolo = " ";
        return null;
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
