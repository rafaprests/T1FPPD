import java.util.Random;

public class ThreadChave extends Thread {
    private ElementoMapa chave;
    private Jogo jogo;

    public ThreadChave(ElementoMapa chave, Jogo jogo) {
        this.chave = chave;
        this.jogo = jogo;
    }

    @Override
    public void run() {
        while (this.chave.getStatus()) {
            try {
                //movimenta a vida
                Random rand = new Random();
                Direcao direcaoAleatoria = Direcao.values()[rand.nextInt(Direcao.values().length)];    
                jogo.getMapa().moveElemento(direcaoAleatoria, chave);

                jogo.repaint();
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
