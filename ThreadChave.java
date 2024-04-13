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
                // Movimenta a chave
                Random rand = new Random();
                Direcao direcaoAleatoria = Direcao.values()[rand.nextInt(Direcao.values().length)];    
                jogo.getMapa().moveElemento(direcaoAleatoria, chave); 

                // Verifica se o personagem está perto da chave
                if (jogo.getMapa().personagemPerto(chave)) {
                    jogo.getMapa().nroChaves++;
                    chave.setStatus(false);
                    jogo.getMapa().removeElemento(chave);
                    jogo.getKeyBar().setText(jogo.desenhaBarraChaves());
                }

                jogo.repaint();
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
