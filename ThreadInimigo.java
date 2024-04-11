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
            // Adicione aqui a lógica para o comportamento do inimigo
            try {
                Random rand = new Random();
                Direcao direcaoAleatoria = Direcao.values()[rand.nextInt(Direcao.values().length)];
                
                jogo.getMapa().moveInimigo(direcaoAleatoria, inimigo);

                if(jogo.getMapa().personagemPertoInimigo(inimigo)){
                    jogo.getMapa().reduzVidaPersonagem(1);
                    
                }
                jogo.repaint();
                Thread.sleep(100); // Por exemplo, aguarda 1 segundo entre cada ação do inimigo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
