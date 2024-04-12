import java.util.Random;

public class ThreadInimigo extends Thread {
    private Inimigo inimigo;
    private Jogo jogo;

    public ThreadInimigo(Inimigo inimigo, Jogo jogo) {
        this.inimigo = inimigo;
        this.jogo = jogo;
    }

    @Override
    public void run() {
        while (this.inimigo.getVida() > 0) {
            try {
                Random rand = new Random();
                Direcao direcaoAleatoria = Direcao.values()[rand.nextInt(Direcao.values().length)];
                
                if(jogo.getMapa().moveElemento(direcaoAleatoria, inimigo))

                if(jogo.getMapa().personagemPerto(inimigo)){
                    jogo.getMapa().reduzVidaPersonagem(1);                    
                }
                jogo.repaint();
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
