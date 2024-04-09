public class ThreadInimigo extends Thread {
    private Inimigo inimigo;
    private Mapa mapa;

    public ThreadInimigo(Inimigo inimigo, Mapa mapa) {
        this.inimigo = inimigo;
        this.mapa = mapa;
    }

    @Override
    public void run() {
        while (true) {
            // Adicione aqui a lógica para o comportamento do inimigo
            // Por exemplo, você pode fazer o inimigo se mover aleatoriamente ou atacar em intervalos regulares
            try {
                Thread.sleep(1000); // Por exemplo, aguarda 1 segundo entre cada ação do inimigo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
