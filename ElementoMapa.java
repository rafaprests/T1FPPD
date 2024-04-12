import java.awt.Color;

public interface ElementoMapa {
    // Retorna o caractere que será usado para desenhar o elemento no mapa
    String getSimbolo();

    // Retorna a cor que será usada para desenhar o elemento no mapa
    Color getCor();

    // Retorna se o elemento pode ser atravessado
    boolean podeSerAtravessado();

    // Retorna se o elemento pode ser interagido
    boolean podeInteragir();

    // Retorna a mensagem de interação do elemento
    String interage();

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);
}
