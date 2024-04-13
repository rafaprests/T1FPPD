import java.awt.Color;

public class Vida implements ElementoMapa {
    private Color cor;
    private Color corPisca;
    private String simbolo;
    private int x;
    private int y;
    private boolean status;
    private final int quantidadeVida = 10;

    public Vida(String simbolo, Color cor) {
        this.simbolo = simbolo;
        this.corPisca = cor;
        this.cor = cor;
        this.status = true;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
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

    public int getQuantidadeVida(){
        return this.quantidadeVida;
    }

    public void piscar(){
        if (cor.equals(corPisca)) {
            cor = new Color(0, 255, 0);
        } else {
            cor = corPisca;
        }
    }
}
