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

    public String atacar(int posXPers, int posYPers, int tamanhoCelula, Mapa mapa) {
        // O inimigo está atacando em sua própria célula, então não é necessário calcular distância
        // Verifica se o personagem está na mesma célula do inimigo
        if (posXPers % tamanhoCelula == 0 && posYPers % tamanhoCelula == 0) {
            // O personagem está na mesma célula que o inimigo, realiza o ataque
            mapa.reduzVidaPersonagem(1);
            return "O inimigo atacou o personagem!";
        } else {
            // O personagem não está na mesma célula que o inimigo
            return "O inimigo não pode atacar.";
        }
    }
}
