import java.util.Random;

/**
 * klasa odpowiadajaca za ksztalty
 */
public class Ksztalt{
    /**
     * Typ wyliczeniowy przechowujacy wszytskie dostepne rodzaje ksztaltow
     */
    protected enum Tetromino {
        Pusty, Z, S, I, T, O, L, J, Zloty, Bomba
    }
    /**
     * zmienna wyliczeniowa opisujaca ksztalt
     */
    private Tetromino ksztaltKlocka;
    /**
     * tablica przechowujaca wspĂłĹ‚rzÄ™dne ksztaltu
     */
    private float[][] koordynaty;
    /**
     * Konstruktor klasy ustawiajacy wspolrzedne danych elementow tworzacych ksztalt
     */
    public Ksztalt() {
        koordynaty = new float[4][2];
        ustawKsztalt(Tetromino.Pusty);
    }
    /**
     * Funkcja skĹ‚adajÄ…ca siÄ™ z tablicy przechowujacej wszystkie mozliwe wartosci wspolrzednych ksztaltu
     * i ustawiajaca jeden wiersz wartosci wspolrzednych
     */
    void ustawKsztalt(Tetromino ksztalt) {

        float[][][] Uklad = new float[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}}
        };

        for (int i = 0; i < 4; i++) {
            System.arraycopy(Uklad[ksztalt.ordinal()], 0, koordynaty, 0, 4);
        }
        ksztaltKlocka = ksztalt;
    }
    /**
     * Funkcja ustawiajaca wspolrzedna X dla konkretnego ksztaltu
     * @param index wskazuje na jeden z 4 fragmentow ("oczko") klocka
     * @param x wskazuje wspolrzedna X
     */
    private void ustawX(int index, float x) {
        koordynaty[index][0] = x;
    }
    /**
     * Funkcja ustawiajaca wspolrzedna Y dla konkretnego ksztaltu
     * @param index wskazuje na jeden z 4 fragmentow ("oczko") klocka
     * @param y wskazuje wspolrzedna Y
     */
    private void ustawY(int index, float y) {
        koordynaty[index][1] = y;
    }
    /**
     * Funkcja zwracajaca wspolrzedna X danego ksztaltu
     * @param index wskazuje na jeden z 4 fragmentow ("oczko") klocka
     */
    float x(int index) {
        return koordynaty[index][0];
    }
    /**
     * Funkcja zwracajaca wspolrzedna Y
     * @param index wskazuje na jeden z 4 fragmentow ("oczko") klocka
     */
    float y(int index) {
        return koordynaty[index][1];
    }
    /**
     * Funkcja zwracjaca ksztalt
     */
    Tetromino pobierzKsztalt() {
        return ksztaltKlocka;
    }
    /**
     * Funkcja ustawiajaca klocek z roznym prawdopodobienstwem
     * - klocki bonusowe ustawiane sa z innym prawdopodobienstwem niz klocki podstawowe
     */
    void ustawRandomKsztalt() {
        var rr = new Random();
        int ran = rr.nextInt(100);
        int x;
        if(ran>Config.pstwoBonusow) {
            var r = new Random();
            x = Math.abs(r.nextInt()) % 7 + 1;
        }
        else {
            var r = new Random();
            x = Math.abs(r.nextInt()) % 2 + 1;
            x = x+7;
        }
        Tetromino[] values = Tetromino.values();
        ustawKsztalt(values[x]);
    }
    /**
     * Funkcja dodajaca konkretny ksztalt
     * @param ind oznacza rodzaj klocka
     */
    void ustawKonkretnyKsztalt(int ind) {

        Tetromino[] values = Tetromino.values();
        ustawKsztalt(values[ind]);
    }
    /**
     * Funkcja zwracajaca najmniejsza wspolrzedna Y dla konkretnego ksztaltu
     */
    public float minY() {
        float m = koordynaty[0][1];

        for (int i = 0; i < 4; i++) {
            m = Math.min(m, koordynaty[i][1]);
        }
        return m;
    }
    /**
     * Funkcja umozliwiajaca rotacje klocka
     */
    Ksztalt obrocWLewo() {
        if (ksztaltKlocka == Tetromino.O) {
            return this;
        }
        var wynik = new Ksztalt();
        wynik.ksztaltKlocka = ksztaltKlocka;

        for (int i = 0; i < 4; i++) {
            wynik.ustawX(i, y(i));
            wynik.ustawY(i, -x(i));
        }
        return wynik;
    }
}
