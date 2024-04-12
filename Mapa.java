import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapa {
    private List<String> mapa;
    private Map<Character, ElementoMapa> elementos;
    private List<Inimigo> inimigos;
    private List<Vida> vidas;
    private int x = 50; // Posição inicial X do personagem
    private int y = 50; // Posição inicial Y do personagem
    private final int TAMANHO_CELULA = 10; // Tamanho de cada célula do mapa
    private boolean[][] areaRevelada; // Rastreia quais partes do mapa foram reveladas
    private final Color brickColor = new Color(153, 76, 0); // Cor marrom para tijolos
    private final Color vegetationColor = new Color(34, 139, 34); // Cor verde para vegetação
    private final Color redColor = new Color(225, 0, 0); // Cor verde para vegetação
    private final Color blueColor = new Color(0, 0, 255); // Cor verde para vegetação
    private final int RAIO_VISAO = 5; // Raio de visão do personagem
    private final int RAIO_MORTE = 2;
    private int vidaPersonagem;

    public Mapa(String arquivoMapa) {
        mapa = new ArrayList<>();
        elementos = new HashMap<>();
        inimigos = new ArrayList<>();
        vidas = new ArrayList<>();
        registraElementos();
        carregaMapa(arquivoMapa);
        inicializaElementos();
        this.vidaPersonagem = 10;
        areaRevelada = new boolean[mapa.size() + 1000][mapa.get(0).length() + 1000];
        atualizaCelulasReveladas();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getTamanhoCelula() {
        return TAMANHO_CELULA;
    }

    public int getNumLinhas() {
        return mapa.size();
    }

    public int getNumColunas() {
        return mapa.get(0).length();
    }

    public ElementoMapa getElemento(int x, int y) {
        Character id = mapa.get(y).charAt(x);
        return elementos.get(id);
    }

    public List<Inimigo> getInimigos() {
        return inimigos;
    }

    public List<Vida> getVidas() {
        return vidas;
    }

    public int getVidaPersonagem(){
        return vidaPersonagem;
    }

    public void setVidaPersonagem(int vida){
        this.vidaPersonagem = vida;
    }
    
    public boolean estaRevelado(int x, int y) {
        return areaRevelada[y][x];
    }

    public boolean move(Direcao direcao) {
        int dx = 0, dy = 0;

        switch (direcao) {
            case CIMA:
                dy = -TAMANHO_CELULA;
                break;
            case BAIXO:
                dy = TAMANHO_CELULA;
                break;
            case ESQUERDA:
                dx = -TAMANHO_CELULA;
                break;
            case DIREITA:
                dx = TAMANHO_CELULA;
                break;
            default:
                return false;
        }

        if (!podeMover(x + dx, y + dy)) {
            System.out.println("Não pode mover o personagem");
            return false;
        }

        x += dx;
        y += dy;

        // Atualiza as células reveladas
        atualizaCelulasReveladas();
        return true;
    }

    public boolean moveElemento(Direcao direcao, ElementoMapa elemento) {
        int dx = 0, dy = 0;

        switch (direcao) {
            case CIMA:
                dy = -TAMANHO_CELULA;
                break;
            case BAIXO:
                dy = TAMANHO_CELULA;
                break;
            case ESQUERDA:
                dx = -TAMANHO_CELULA;
                break;
            case DIREITA:
                dx = TAMANHO_CELULA;
                break;
            default:
                return false;
        }

        if (!podeMover(elemento.getX() + dx, elemento.getY() + dy)) {
            System.out.println("Não pode mover o inimigo");
            return false;
        }

        elemento.setX(elemento.getX() + dx);
        elemento.setY(elemento.getY() + dy);

        // Atualiza as células reveladas
        atualizaCelulasReveladas();
        return true;
    }

    // Verifica se o personagem pode se mover para a próxima posição
    public boolean podeMover(int nextX, int nextY) {
        int mapX = nextX / TAMANHO_CELULA;
        int mapY = nextY / TAMANHO_CELULA - 1;

        if (mapa == null)
            return false;

        if (mapX >= 0 && mapX < mapa.get(0).length() && mapY >= 1 && mapY <= mapa.size()) {
            char id;

            try {
                id = mapa.get(mapY).charAt(mapX);
            } catch (StringIndexOutOfBoundsException e) {
                return false;
            }

            if (id == ' ')
                return true;

            ElementoMapa elemento = elementos.get(id);
            if (elemento != null) {
                // System.out.println("Elemento: " + elemento.getSimbolo() + " " +
                // elemento.getCor());
                return elemento.podeSerAtravessado();
            }
        }

        return false;
    }

    public String ataca() {
        if (mapa == null)
            return "N.A. mapa";

        // Itera sobre cada inimigo para verificar a proximidade com o personagem
        for (Inimigo inimigo : inimigos) {
            int distanciaX = Math.abs(x - inimigo.getX());
            int distanciaY = Math.abs(y - inimigo.getY());
            double distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));

            // Verifica se a distância é menor ou igual ao raio de interação
            if (distancia <= RAIO_MORTE * TAMANHO_CELULA) {
                inimigo.reduzVidaInimigo();
                inimigos.remove(inimigo);
                mapa.set(inimigo.getY() / TAMANHO_CELULA,
                        mapa.get(inimigo.getY() / TAMANHO_CELULA).substring(0, inimigo.getX() / TAMANHO_CELULA)
                                + " "
                                + mapa.get(inimigo.getY() / TAMANHO_CELULA)
                                        .substring(inimigo.getX() / TAMANHO_CELULA + 1));
                return "Você matou o inimigo!";

            }
        }
        return "Nenhum inimigo próximo.";
    }

    public String interage() {
        if (mapa == null)
            return "N.A. mapa";

        // Itera sobre cada inimigo para verificar a proximidade com o personagem
        for (Vida vida : vidas) {
            int distanciaX = Math.abs(x - vida.getX());
            int distanciaY = Math.abs(y - vida.getY());
            double distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));

            // Verifica se a distância é menor ou igual ao raio de interação
            if (distancia <= RAIO_MORTE * TAMANHO_CELULA) {
                setVidaPersonagem(vida.getQuantidadeVida());
                vidas.remove(vida);
                mapa.set(vida.getY() / TAMANHO_CELULA,
                        mapa.get(vida.getY() / TAMANHO_CELULA).substring(0, vida.getX() / TAMANHO_CELULA)
                                + " "
                                + mapa.get(vida.getY() / TAMANHO_CELULA)
                                        .substring(vida.getX() / TAMANHO_CELULA + 1));
                return "Você ganhou 25 de vida!";

            }
        }
        return "Nenhum inimigo próximo.";

    }

    private void carregaMapa(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                mapa.add(line);
                // Se character 'P' está contido na linha atual, então define a posição inicial
                // do personagem
                if (line.contains("P")) {
                    x = line.indexOf('P') * TAMANHO_CELULA;
                    y = mapa.size() * TAMANHO_CELULA;
                    // Remove o personagem da linha para evitar que seja desenhado
                    mapa.set(mapa.size() - 1, line.replace('P', ' '));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // inicializacao dos inimigos
    private void inicializaElementos() {
        for (int i = 0; i < mapa.size(); i++) {
            String linha = mapa.get(i);
            StringBuilder novaLinha = new StringBuilder(linha);
            for (int j = 0; j < linha.length(); j++) {
                char id = linha.charAt(j);
                if (id == 'W') {
                    //substitui o W por ' '
                    novaLinha.setCharAt(j, ' ');
                    
                    //inicializa novo inimigo
                    Inimigo novoInimigo = new Inimigo("O", redColor);
                    novoInimigo.setX(j * TAMANHO_CELULA);
                    novoInimigo.setY(i * TAMANHO_CELULA);
                    inimigos.add(novoInimigo);
                }
                if(id == 'C'){
                    //substitui o C por ' '
                    novaLinha.setCharAt(j, ' ');

                    //inicializa nova vida
                    Vida novaVida = new Vida("+", blueColor);
                    novaVida.setX(j * TAMANHO_CELULA);
                    novaVida.setY(i * TAMANHO_CELULA);
                    vidas.add(novaVida);
                }
                //substitui a linha que continha o caracter W pela linha do strinbuilder
                mapa.set(i, novaLinha.toString());
            }
        }
    }

    // Método para atualizar as células reveladas
    private void atualizaCelulasReveladas() {
        if (mapa == null)
            return;
        for (int i = Math.max(0, y / TAMANHO_CELULA - RAIO_VISAO); i < Math.min(mapa.size(),
                y / TAMANHO_CELULA + RAIO_VISAO + 1); i++) {
            for (int j = Math.max(0, x / TAMANHO_CELULA - RAIO_VISAO); j < Math.min(mapa.get(i).length(),
                    x / TAMANHO_CELULA + RAIO_VISAO + 1); j++) {
                areaRevelada[i][j] = true;
            }
        }
    }

    // Registra os elementos do mapa
    private void registraElementos() {
        elementos.put('#', new Parede("▣", brickColor));
        elementos.put('V', new Vegetacao("♣", vegetationColor));
        elementos.put('W', new Inimigo("O", redColor));
        elementos.put('C', new Vida("+", blueColor));
    }

    public void reduzVidaPersonagem(int quantidade) {
        this.vidaPersonagem -= quantidade;
        if (this.vidaPersonagem <= 0) {
            // Aqui você pode adicionar lógica para tratar o fim do jogo, como exibir uma
            // mensagem de "Game Over"
            System.out.println("Game Over - Personagem morreu!");
        }
    }

    public boolean personagemPerto(ElementoMapa elemento) {
        int distanciaX = Math.abs(x - elemento.getX());
        int distanciaY = Math.abs(y - elemento.getY());
        double distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));

        // Verifica se a distância é menor ou igual ao raio de visão
        if (distancia <= RAIO_MORTE * TAMANHO_CELULA) {
            return true; 
        }

        return false; 
    }
}
