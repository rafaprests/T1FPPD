public class ThreadInimigo extends Thread {
    private Inimigo inimigo;
    private Mapa mapa;

    public ThreadInimigo(Inimigo inimigo, Mapa mapa) {
        this.inimigo = inimigo;
        this.mapa = mapa;
    }

    @Override
    public void run() {
        while (this.inimigo.getVida() > 0) {
            // Adicione aqui a lógica para o comportamento do inimigo
            try {
                if(mapa.personagemPertoInimigo()){
                    mapa.reduzVidaPersonagem(1);
                    
                }
                Thread.sleep(1000); // Por exemplo, aguarda 1 segundo entre cada ação do inimigo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
