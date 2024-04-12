import java.util.Random;

public class ThreadVida extends Thread {
    private Vida vida;
    private Jogo jogo;

    public ThreadVida(Vida vida, Jogo jogo) {
        this.vida = vida;
        this.jogo = jogo;
    }

    @Override
    public void run() {
        while (this.vida.getStatus()) {
            try {
                //movimenta a vida
                Random rand = new Random();
                Direcao direcaoAleatoria = Direcao.values()[rand.nextInt(Direcao.values().length)];    
                jogo.getMapa().moveElemento(direcaoAleatoria, vida);

                //faz a vida piscar
                vida.piscar();

                jogo.repaint();
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
