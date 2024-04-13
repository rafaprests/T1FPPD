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
    private List<ElementoMapa> listaElementos;
    private int x;
    private int y;
    public final int TAMANHO_CELULA = 10;
    private boolean[][] areaRevelada;
    private final Color redColor = new Color(225, 0, 0);
    private final Color blueColor = new Color(0, 0, 255);
    private final Color goldColor = new Color(218, 165, 32);
    public final int RAIO_VISAO = 5;
    public final int RAIO_MORTE = 2;
    public final int VIDAMAXIMA = 25;
    private int vidaPersonagem = 25;
    public int nroChaves = 0;
    private boolean gameOn;

    public Mapa(String arquivoMapa) {
        mapa = new ArrayList<>();
        elementos = new HashMap<>();
        listaElementos = new ArrayList<>();
        registraElementos();
        carregaMapa(arquivoMapa);
        inicializaElementos();
        areaRevelada = new boolean[mapa.size() + 1000][mapa.get(0).length() + 1000];
        atualizaCelulasReveladas();
        gameOn = true;
    }

    public boolean getGameOn(){
        return gameOn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVidaMaxima() {
        return VIDAMAXIMA;
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

    public List<ElementoMapa> getListaElementos() {
        return listaElementos;
    }

    public List<String> getMapa() {
        return mapa;
    }

    public int getVidaPersonagem() {
        return vidaPersonagem;
    }

    public void setVidaPersonagem(int vida) {
        if (vidaPersonagem + vida >= VIDAMAXIMA) {
            this.vidaPersonagem = VIDAMAXIMA;
        } else {
            vidaPersonagem += vida;
        }
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
            return false;
        }

        x += dx;
        y += dy;

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

        // garante que o inimigo nao atravessa o personagem
        if (elemento instanceof Inimigo && elemento.getX() + dx == x && elemento.getY() + dy == y)
            return false;

        if (!podeMover(elemento.getX() + dx, elemento.getY() + dy)) {
            return false;
        }

        elemento.setX(elemento.getX() + dx);
        elemento.setY(elemento.getY() + dy);

        atualizaCelulasReveladas();
        return true;
    }

    public boolean podeMover(int nextX, int nextY) {
        int mapX = nextX / TAMANHO_CELULA;
        int mapY = nextY / TAMANHO_CELULA - 1;

        if (mapa == null)
            return false;

        for (int i = 0; i < listaElementos.size(); i++) {
            if (nextX == listaElementos.get(i).getX() && nextY == listaElementos.get(i).getY()) {
                return listaElementos.get(i).podeSerAtravessado();
            }
        }

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
                return elemento.podeSerAtravessado();
            }
        }

        return false;
    }

    public String ataca() {
        if (mapa == null)
            return "N.A. mapa";

        // Itera sobre cada inimigo para verificar a proximidade com o personagem
        for (ElementoMapa elemento : listaElementos) {
            int distanciaX = Math.abs(x - elemento.getX());
            int distanciaY = Math.abs(y - elemento.getY());
            double distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));

            // Verifica se a distância é menor ou igual ao raio de interação
            if (elemento instanceof Inimigo) {
                if (distancia <= RAIO_MORTE * TAMANHO_CELULA) {
                    elemento.interage();
                    removeElemento(elemento);
                    return "Você matou o inimigo!";
                }
            }
        }
        return "Nenhum inimigo próximo.";
    }

    public String interage() {
        if (mapa == null)
            return "N.A. mapa";

        // Itera sobre cada elemento para verificar a proximidade com o personagem
        for (ElementoMapa elemento : listaElementos) {
            int distanciaX = Math.abs(x - elemento.getX());
            int distanciaY = Math.abs(y - elemento.getY());
            double distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));

            // Verifica se a distância é menor ou igual ao raio de interação
            if (distancia <= RAIO_MORTE * TAMANHO_CELULA) {
                if (elemento instanceof Vida) {
                    setVidaPersonagem(elemento.getQuantidadeVida());
                    removeElemento(elemento);
                    return "Você ganhou 10 de vida!";
                }
            }
        }

        // Verifica se a porta está próxima ao jogador
        int mapX = x / TAMANHO_CELULA;
        int mapY = y / TAMANHO_CELULA;

        for (int i = Math.max(0, mapY - RAIO_MORTE); i < Math.min(mapa.size(), mapY + RAIO_MORTE + 1); i++) {
            for (int j = Math.max(0, mapX - RAIO_MORTE); j < Math.min(mapa.get(i).length(),
                    mapX + RAIO_MORTE + 1); j++) {
                char id = mapa.get(i).charAt(j);
                if (id == 'D') {
                    ElementoMapa elemento = elementos.get(id);
                    if (elemento instanceof Porta) {
                        if (nroChaves == 5) {
                            elemento.interage();
                            removeElemento(elemento);
                            gameOn = false;
                            return null;
                        } else {
                            return "Você precisa de 5 chaves para abrir a porta.";
                        }
                    }
                }
            }
        }
        return "Nada interativo por aqui...";
    }

    private void carregaMapa(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                mapa.add(line);
                if (line.contains("P")) {
                    x = line.indexOf('P') * TAMANHO_CELULA;
                    y = mapa.size() * TAMANHO_CELULA;
                    mapa.set(mapa.size() - 1, line.replace('P', ' '));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // inicializacao dos elementos
    private void inicializaElementos() {
        for (int i = 0; i < mapa.size(); i++) {
            String linha = mapa.get(i);
            StringBuilder novaLinha = new StringBuilder(linha);
            for (int j = 0; j < linha.length(); j++) {
                char id = linha.charAt(j);
                if (id == 'W') {
                    // substitui o W por ' '
                    novaLinha.setCharAt(j, ' ');

                    // inicializa novo inimigo
                    Inimigo novoInimigo = new Inimigo("☠", redColor);
                    novoInimigo.setX(j * TAMANHO_CELULA);
                    novoInimigo.setY(i * (TAMANHO_CELULA + 1));
                    listaElementos.add(novoInimigo);
                }
                if (id == 'C') {
                    // substitui o C por ' '
                    novaLinha.setCharAt(j, ' ');

                    // inicializa nova vida
                    Vida novaVida = new Vida("♥", blueColor);
                    novaVida.setX(j * TAMANHO_CELULA);
                    novaVida.setY(i * (TAMANHO_CELULA + 1));
                    listaElementos.add(novaVida);
                }
                if (id == 'K') {
                    // substitui o K por ' '
                    novaLinha.setCharAt(j, ' ');

                    // inicializa nova chave
                    Chave novaChave = new Chave("⚷", goldColor);
                    novaChave.setX(j * TAMANHO_CELULA);
                    novaChave.setY(i * (TAMANHO_CELULA + 1));
                    listaElementos.add(novaChave);
                }
                // substitui a linha que continha o caracter W pela linha do strinbuilder
                mapa.set(i, novaLinha.toString());
            }
        }
    }

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

    private void registraElementos() {
        elementos.put('#', new Parede("▣", new Color(153, 76, 0)));
        elementos.put('V', new Vegetacao("♣", new Color(34, 139, 34)));
        elementos.put('D', new Porta("▣", new Color(0, 0, 0)));
        elementos.put('W', new Inimigo("☠", redColor));
        elementos.put('C', new Vida("♥", blueColor));
        elementos.put('K', new Chave("⚷", goldColor));
    }

    public void reduzVidaPersonagem(int quantidade) {
        if(this.vidaPersonagem > 0)
            this.vidaPersonagem -= quantidade;
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

    private void atualizaMapa(int x, int y, char newChar) {
        int cellX = x / TAMANHO_CELULA;
        int cellY = y / TAMANHO_CELULA;

        StringBuilder linha = new StringBuilder(mapa.get(cellY));
        int startIndex = cellX * TAMANHO_CELULA;
        int endIndex = startIndex + TAMANHO_CELULA;

        // Garante que o caractere substituído mantenha o tamanho da célula
        for (int i = startIndex; i < endIndex; i++) {
            if (i >= linha.length()) {
                linha.append(newChar);
            } else {
                linha.setCharAt(i, newChar);
            }
        }

        mapa.set(cellY, linha.toString());
    }

    // Método para remover um elemento do mapa
    public void removeElemento(ElementoMapa elemento) {
        int x = elemento.getX();
        int y = elemento.getY();
        char newChar = ' '; // Caractere para substituir o elemento removido

        // Atualiza a célula no mapa
        atualizaMapa(x, y, newChar);

        // Remove o elemento da lista de elementos
        listaElementos.remove(elemento);
    }
}
