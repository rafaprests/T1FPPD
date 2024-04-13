import java.awt.Color;

public class Chave implements ElementoMapa {
    private Color cor;
    private String simbolo;
    private double x;
    private double y;
    private boolean status;

    public Chave(String simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
        this.status = true;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
    
    public String getSimbolo() {
        return simbolo;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor){
        this.cor = cor;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean b){
        this.status = b;
    }

    @Override
    public boolean podeSerAtravessado() {
        return true;
    }

    @Override
    public boolean podeInteragir() {
        return true;
    }

    @Override
    public String interage() {
        System.out.println("Mais 25 de vida!");
        return null;
    }

    @Override
    public int getQuantidadeVida() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQuantidadeVida'");
    }

    @Override
    public void piscar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'piscar'");
    }
}
