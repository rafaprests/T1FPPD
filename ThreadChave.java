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
                if(chave.getX() == jogo.getMapa().getX() && chave.getY() == jogo.getMapa().getY()){
                    jogo.getMapa().nroChaves++;
                    chave.setStatus(false);
                        jogo.getMapa().getListaElementos().remove(chave);
                        jogo.getMapa().getMapa().set(chave.getY() / jogo.getMapa().TAMANHO_CELULA,
                                jogo.getMapa().getMapa().get(chave.getY() / jogo.getMapa().TAMANHO_CELULA).substring(0, chave.getX() / jogo.getMapa().TAMANHO_CELULA)
                                        + " "
                                        + jogo.getMapa().getMapa().get(chave.getY() / jogo.getMapa().TAMANHO_CELULA)
                                                .substring(chave.getX() / jogo.getMapa().TAMANHO_CELULA + 1));
                } 
                jogo.getKeyBar().setText(jogo.desenhaBarraChaves());

                jogo.repaint();
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
