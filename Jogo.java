import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Jogo extends JFrame implements KeyListener {
    private JLabel statusBar;
    private Mapa mapa;
    private final Color fogColor = new Color(192, 192, 192, 150); // Cor cinza claro com transparência para nevoa
    private final Color characterColor = Color.BLACK; // Cor preta para o personagem

    public Jogo(String arquivoMapa) {
        setTitle("Jogo de Aventura");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Cria o mapa do jogo
        mapa = new Mapa(arquivoMapa);

        // Painel para desenhar o mapa do jogo
        JPanel mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Define a fonte para garantir que o caractere caiba em 10x10 pixels
                Font font = new Font("Roboto", Font.BOLD, 12);
                g.setFont(font);
                desenhaMapa(g);
                desenhaElementos(g);
                desenhaPersonagem(g);
            }
        };
        mapPanel.setPreferredSize(new Dimension(800, 600));

        // Botões de movimento
        JButton btnUp = new JButton("Cima (W)");
        JButton btnDown = new JButton("Baixo (S)");
        JButton btnRight = new JButton("Direita (D)");
        JButton btnLeft = new JButton("Esquerda (A)");
        JButton btnInterect = new JButton("Ataca (E)");
        JButton btnAttack = new JButton("Ação Secundária (J)");

        // Evita que os botões recebam o foco e interceptem os eventos de teclado
        btnUp.setFocusable(false);
        btnDown.setFocusable(false);
        btnRight.setFocusable(false);
        btnLeft.setFocusable(false);
        btnInterect.setFocusable(false);
        btnAttack.setFocusable(false);

        // Listeners para os botões
        btnUp.addActionListener(e -> move(Direcao.CIMA));
        btnDown.addActionListener(e -> move(Direcao.BAIXO));
        btnRight.addActionListener(e -> move(Direcao.DIREITA));
        btnLeft.addActionListener(e -> move(Direcao.ESQUERDA));
        btnInterect.addActionListener(e -> interage());
        btnAttack.addActionListener(e -> ataca());

        // Layout dos botões
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(btnUp);
        buttonPanel.add(btnDown);
        buttonPanel.add(btnInterect);
        buttonPanel.add(btnRight);
        buttonPanel.add(btnLeft);
        buttonPanel.add(btnAttack);

        statusBar = new JLabel(desenhaBarraStatus());
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);

        // Painel para botões e barra de status
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.add(statusBar);
        southPanel.add(buttonPanel);

        // Adiciona os paineis ao JFrame
        add(mapPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Ajusta o tamanho do JFrame para acomodar todos os componentes
        pack();

        // Adiciona o listener para eventos de teclado
        addKeyListener(this);

        iniciarThreadsElementos();;
    }

    // Método para iniciar as threads
    public void iniciarThreadsElementos() {
        List<ElementoMapa> listaElementos = mapa.getListaElementos();

        for (ElementoMapa elemento : listaElementos) {
            if(elemento instanceof Inimigo){
                ThreadInimigo threadInimigo = new ThreadInimigo(elemento, this);
                threadInimigo.start();
            }
            if(elemento instanceof Vida){
                ThreadVida threadVida = new ThreadVida(elemento, this);
                threadVida.start();
            }
            if(elemento instanceof Chave){
                ThreadChave threadChave = new ThreadChave(elemento, this);
                threadChave.start();
            }
            
        }
    }

    public void move(Direcao direcao) {
        if (mapa == null)
            return;

        // Modifica posição do personagem no mapa
        if (!mapa.move(direcao))
            return;

        // Atualiza a barra de status
        if (statusBar != null)
            statusBar.setText(desenhaBarraStatus());

        // Redesenha o painel
        repaint();
    }

    public void ataca() {
        if (mapa == null)
            return;

        String mensagem = mapa.ataca();
        if (mensagem != null) {
            JOptionPane.showMessageDialog(this, mensagem);
        }
    }

    public void interage() {
        if (mapa == null)
            return;

        String mensagem = mapa.interage();
        if (mensagem != null) {
            JOptionPane.showMessageDialog(this, mensagem);
        }
    }

    private void desenhaMapa(Graphics g) {
        int tamanhoCelula = mapa.getTamanhoCelula();
        for (int i = 0; i < mapa.getNumLinhas(); i++) {
            for (int j = 0; j < mapa.getNumColunas(); j++) {
                int posX = j * tamanhoCelula;
                int posY = (i + 1) * tamanhoCelula;

                if (mapa.estaRevelado(j, i)) {
                    ElementoMapa elemento = mapa.getElemento(j, i);
                    if (elemento != null) {
                        g.setColor(elemento.getCor());
                        g.drawString(elemento.getSimbolo().toString(), posX, posY);

                    }
                } else {
                    // Pinta a área não revelada
                    g.setColor(fogColor);
                    g.fillRect(j * tamanhoCelula, i * tamanhoCelula, tamanhoCelula, tamanhoCelula);
                }
            }
        }
    }

    private void desenhaPersonagem(Graphics g) {
        g.setColor(characterColor);
        g.drawString("☺", mapa.getX(), mapa.getY());
    }

    private void desenhaElementos(Graphics g) {
        for (int i = 0; i < mapa.getListaElementos().size(); i++) {
            g.setColor(mapa.getListaElementos().get(i).getCor());
            g.drawString(mapa.getListaElementos().get(i).getSimbolo(), mapa.getListaElementos().get(i).getX(),
                    mapa.getListaElementos().get(i).getY());
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não necessário
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_W: // Tecla 'W' para cima
                move(Direcao.CIMA);
                break;
            case KeyEvent.VK_S: // Tecla 'S' para baixo
                move(Direcao.BAIXO);
                break;
            case KeyEvent.VK_A: // Tecla 'A' para esquerda
                move(Direcao.ESQUERDA);
                break;
            case KeyEvent.VK_D: // Tecla 'D' para direita
                move(Direcao.DIREITA);
                break;
            case KeyEvent.VK_E: // Tecla 'E' para atacar
                ataca();
                break;
            case KeyEvent.VK_J: // Tecla 'J' para ação secundária
                interage();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Não necessário
    }

    public String desenhaBarraStatus() {
        StringBuilder s = new StringBuilder();
        s.append("<html><font color=\"red\">");
        for (int i = 0; i < mapa.getVidaMaxima(); i++) {
            if (i < mapa.getVidaPersonagem()) {
                // s.append("■");
                s.append("♥");
                
            } else {
                // s.append("□");
                s.append("♡");
                
            }
        }
        s.append("</font></html>");
        return s.toString();
    }

    public Mapa getMapa() {
        return mapa;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogo jogo = new Jogo("mapa.txt");
            jogo.setVisible(true);
            //jogo.iniciarThreadsElementos();
        });
    }
}
