import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.Math;

/**
 * Klasa odpowiadajaca za plansze gry
 */
public class Plansza extends JPanel {
    /**
     * Zmienna prywatna ostateczna typu int przechowujaca liczbe wierszy
     */
    private final int SZEROKOSC_PLANSZY = Config.liczbaKolumn;
    /**
     * Zmienna prywatna ostateczna typu int przechowujaca liczbe kolumn
     */
    private final int WYSOKOSC_PLANSZY = Config.liczbaWierszy;
    /**
     * Zmienna prywatna typu int przechowujaca informacje o predkosci opadania klocka
     * Im mniejsza wartosc tym wieksza predkosc klocka
     */
    private int PREDKOSC = Config.predkosc;

    private Timer timer;
    /**
     * Zmienna  typu boolean przechowujaca informacje czy klocek zakonczyl juz opadanie
     */
    private boolean czyKlocekOpadl = false;
    /**
     * Zmienna  typu boolean przechowujaca informacje czy gra jest w trybie pauzy
     */
    private boolean czyPauza = false;
    /**
     * Zmienna  typu int przechowujaca aktualna liczbe usunietych w rozgrywce pelnych linii
     */
    private int liczbaUsunietychLinii = 0;
    /**
     * Etykieta zawierajaca aktualny status rozgrywki
     */
    private Label statusbar;
    /**
     * Zmienna  typu int przechowujaca aktualna wspolrzedna X
     */
    private float aktualnyX = 0;
    /**
     * Zmienna  typu int przechowujaca aktualna wspolrzedna Y
     */
    private float aktualnyY = 0;
    /**
     * Zmienna  typu int przechowujaca aktualny ksztalt
     */
    private Ksztalt aktualnyKsztalt;
    /**
     * zmienna siatki
     */
    private Ksztalt.Tetromino[] plansza;
    /**
     * Etykieta zawierajaca informacje o aktualnym statusie rozgywki:
     * etykieta pusta - toczy sie rozgrywka
     * "YOU WIN","GAME OVER" - rozgrywka sie zakonczyla
     * "PAUZA" - rozgrywka jest zapauzowana
     */
    private Label status;
    /**
     * Etykieta zawierajaca informacje o poziomie, na ktorym znajduje sie uzytkownik
     */
    private Label lvl;
    /**
     * Etykieta zawierajaca informacje o liczbie ulozonych przez gracza linii
     */
    private Label linie;
    /**
     * Etykieta zawierajaca informacje o liczbie punktow zdpbytych przez gracza
     */
    private Label wynik;
    /**
     * Zmienna  typu int przechowujaca poczatkowa wartosc poziomu
     */
    private int poziom = 1;
    /**
     * Tablica przechowujaca flagi wskazujace aktualny poziom
     */
    private boolean[] flagi = {true, false, false, false, false, false, false};
    /**
     * Zmienna  typu int przechowujaca prog punktowy potrzebny do przejscia na kolejny poziom
     */
    private int prog = Config.progPunktowy;
    /**
     * Dynamicznie tworzona tablica wynikow
     */
    TablicaWynikow tab = new TablicaWynikow();
    /**
     * Zmienna typu int przechowujaca liczbe zlotych klockow znajdujacych sie w pelnych liniach
     */
    private int ileZlotychWPelnychLiniach = 0;

    /**
     * Konstruktor tworzacy plansze w oknie gry
     */
    public Plansza(OknoGry parent) {
        stworzPlansze(parent);
    }

    /**
     * Funkcja tworzaca plansze
     */
    private void stworzPlansze(OknoGry parent) {
        setFocusable(true);
        status = parent.pobierzStatus();
        lvl = parent.pobierzPoziom();
        linie = parent.pobierzLinie();
        wynik = parent.pobierzWynik();
        addKeyListener(new Adapter());
    }

    /**
     * funkcja ustlajaca szerokosc elementu ("oczka") ksztaltu
     */
    private int szerokoscElementu() {
        return (int) getSize().getWidth() / SZEROKOSC_PLANSZY;
    }

    /**
     * funkcja ustlajaca wysokosc elementu ("oczka") ksztaltu
     */
    private int wysokoscElementu() {
        return (int) getSize().getHeight() / (WYSOKOSC_PLANSZY);
    }

    /**
     * funkcja ustawiajaca ksztalt na planszy
     * @param x to wspolrzedna X
     * @param y to wspolrzedna Y
     */
    private Ksztalt.Tetromino ksztaltW(float x, float y) {
        return plansza[(int)((y * SZEROKOSC_PLANSZY) + x)];
    }

    /**
     * funkcja ustalajaca plansze
     */
    void start() {
        aktualnyKsztalt = new Ksztalt();
        plansza = new Ksztalt.Tetromino[SZEROKOSC_PLANSZY * WYSOKOSC_PLANSZY];

        wyczyscPlansze();
        nowyKlocek();
        try {
            ustawPoziom(poziom);
        } catch (FileNotFoundException e) {
            System.out.println("Brak poziomu");
        }

        timer = new Timer(PREDKOSC, new cyklGry());
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rysuj(g);
    }

    /**
     * funkcja rysyjaca ksztalt
     */
    private void Rysuj(Graphics g) {
        var rozmiar = getSize();
        int szczytPlanszy = (int) rozmiar.getHeight() - WYSOKOSC_PLANSZY * wysokoscElementu();

        for (int i = 0; i < WYSOKOSC_PLANSZY; i++) {
            for (int j = 0; j < SZEROKOSC_PLANSZY; j++) {
                Ksztalt.Tetromino ksztalt = ksztaltW(j, WYSOKOSC_PLANSZY - i - 1);

                if (ksztalt != Ksztalt.Tetromino.Pusty) {
                    rysujElement(g, j * szerokoscElementu(),
                            szczytPlanszy + i * wysokoscElementu(), ksztalt);
                }
            }
        }
        if (aktualnyKsztalt.pobierzKsztalt() != Ksztalt.Tetromino.Pusty) {
            for (int i = 0; i < 4; i++) {
                float x = aktualnyX + aktualnyKsztalt.x(i);
                float y = aktualnyY - aktualnyKsztalt.y(i);
                rysujElement(g, (int)x * szerokoscElementu(),
                        szczytPlanszy + (int)(WYSOKOSC_PLANSZY - y - 1) * wysokoscElementu(),
                        aktualnyKsztalt.pobierzKsztalt());
            }
        }
    }

    /**
     * funkcja sluzaca do upuszczenia spadajacego klocka na dol planszy
     */
    private void upuscNaDol() {
        float nowyY = aktualnyY;
        while (nowyY > 0) {
            if (!sprobujRuszyc(aktualnyKsztalt, aktualnyX, nowyY - 1)) {
                break;
            }
            nowyY--;
        }
        elementOpuszczony();
    }

    /**
     * funkcja sluzaca do przemieszczenia klocka jedna linie w dol
     */
    private void jednaLiniaWDol() {
        if (!sprobujRuszyc(aktualnyKsztalt, aktualnyX, aktualnyY - 1)) {
            elementOpuszczony();
        }
    }

    /**
     * funkcja przesuwajaca kazdy element ("oczko") w dol
     */
    private void elementOpuszczony() {
        for (int i = 0; i < 4; i++) {
            float x = aktualnyX + aktualnyKsztalt.x(i);
            float y = aktualnyY - aktualnyKsztalt.y(i);
            plansza[(int)((y * SZEROKOSC_PLANSZY) + x)] = aktualnyKsztalt.pobierzKsztalt();
        }
        usunPelneLinie();
        bomba();
        if (!czyKlocekOpadl) {
            nowyKlocek();
        }
    }

    /**
     * funkcja pozwalajaca przemieszczaÄ‡, ruszac klocek po planszy
     * @param nowyKsztalt okresla rozpatrywany ksztalt
     * @param nowyX to nowa wspolrzedna X, na ktorej ma sie znalezc rozptrywany ksztalt
     * @param nowyY to nowa wspolrzedna Y, na ktorej ma sie znalezc rozptrywany ksztalt
     */
    private boolean sprobujRuszyc(Ksztalt nowyKsztalt, float nowyX, float nowyY) {
        for (int i = 0; i < 4; i++) {
            float x = nowyX + nowyKsztalt.x(i);
            float y = nowyY - nowyKsztalt.y(i);
            if (x < 0 || x >= SZEROKOSC_PLANSZY || y < 0 || y >= WYSOKOSC_PLANSZY) {
                return false;
            }
            if (ksztaltW(x, y) != Ksztalt.Tetromino.Pusty) {
                return false;
            }
        }
        aktualnyKsztalt = nowyKsztalt;
        aktualnyX = nowyX;
        aktualnyY = nowyY;
        repaint();
        return true;
    }
    /**
     * funkcja sprawdzajaca czy upuszczono bombe i definiujaca jej zachowanie (usuniecie klocka znajdujacego sie pod nia)
     */
    private void bomba(){

        boolean czyJestBomba = false;
        int i,j = 0;
        for (i = WYSOKOSC_PLANSZY - 1; i >= 0; i--) {
            for (j = 0; j < SZEROKOSC_PLANSZY; j++) {
                if (ksztaltW(j, i) == Ksztalt.Tetromino.Bomba) {
                    czyJestBomba = true;
                    break;
                }
            }
            if(czyJestBomba) {break;}
        }

        if (czyJestBomba) {
            if(i==0) {
                plansza[(i * SZEROKOSC_PLANSZY) + j] = Ksztalt.Tetromino.Pusty;
            }
            else {
                plansza[(i * SZEROKOSC_PLANSZY) + j] = Ksztalt.Tetromino.Pusty;
                plansza[((i-1) * SZEROKOSC_PLANSZY) + j] = Ksztalt.Tetromino.Pusty;
            }
        }
    }


    /**
     * funkcja usuwajaca pelne linie klockow, wprowadzajaca poziomy, sprawdzajaca warunek przejscia gry oraz
     * zliczajaca uzyskane punkty uwzgledniajac bonus za zloty klocek
     */
    private void usunPelneLinie() {
        int liczbaPelnychLinii = 0;
        for (int i = WYSOKOSC_PLANSZY - 1; i >= 0; i--) {
            boolean liniaJestPelna = true;
            for (int j = 0; j < SZEROKOSC_PLANSZY; j++) {
                if (ksztaltW(j, i) == Ksztalt.Tetromino.Pusty) {
                    liniaJestPelna = false;
                    break;
                }
            }

            if (liniaJestPelna) {
                liczbaPelnychLinii++;
                ileZlotychWPelnychLiniach = 0;
                for (int b = 0; b < SZEROKOSC_PLANSZY; b++) {
                    if (ksztaltW(b, i) == Ksztalt.Tetromino.Zloty) {
                        ileZlotychWPelnychLiniach++;
                    }
                }
                for (int k = i; k < WYSOKOSC_PLANSZY - 1; k++) {
                    for (int j = 0; j < SZEROKOSC_PLANSZY; j++) {
                        plansza[(k * SZEROKOSC_PLANSZY) + j] = ksztaltW(j, k + 1);
                    }
                }
            }
        }
        if (liczbaPelnychLinii > 0) {
            liczbaUsunietychLinii += liczbaPelnychLinii;
            int pom2;
            pom2 = (int) (Math.pow(3, (liczbaPelnychLinii)) * 10) + ileZlotychWPelnychLiniach*15;
            wynik.setText(String.valueOf(Integer.parseInt(wynik.getText()) + pom2));
            linie.setText(String.valueOf(liczbaUsunietychLinii));
            czyKlocekOpadl = true;
            aktualnyKsztalt.ustawKsztalt(Ksztalt.Tetromino.Pusty);

            int pom = Integer.parseInt(wynik.getText());
            int a = pom / prog;
            if (a >= 1 && flagi[poziom - 1]) {
                if (pom >= (Config.liczbaPoziomow * prog)) {
                    poziom = a;
                    lvl.setText(String.valueOf(poziom));
                    tab.tablicaWynikow(true, pom);
                    aktualnyKsztalt.ustawKsztalt(Ksztalt.Tetromino.Pusty);
                    timer.stop();
                    status.setText("YOU WIN");
                }
                else if(pom>=(poziom*prog)) {
                    flagi[poziom - 1] = false;
                    flagi[a] = true;
                    poziom = a + 1;
                    lvl.setText(String.valueOf(poziom));
                    timer.stop();
                    start();
                    repaint();
                }
            }
        }
    }

    /**
     * funkcja opisujaca cykl gry
     */
    private class cyklGry implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stworzCyklGry();
        }
    }

    /**
     * funkcja tworzaca cykl gry
     */
    private void stworzCyklGry() {
        aktualizacja();
        repaint();
    }

    /**
     * funkcja decydujaca o aktualizacji rozgrywki
     */
    private void aktualizacja() {
        if (czyPauza) {
            return;
        }
        if (czyKlocekOpadl) {
            czyKlocekOpadl = false;
            nowyKlocek();
        } else {
            jednaLiniaWDol();
        }
    }

    /**
     * funkcja decydujaca co dzieje sie w przypadku zapauzowania gry
     */
    private void pauza() {
        czyPauza = !czyPauza;
        if (czyPauza) {
            status.setText("PAUZA");
        } else {
            status.setText("");
        }
        repaint();
    }

    /**
     * funkcja przypisujaca nacisnietym przez uzytkownika klawiszom odpowiednie reakcje
     */
    class Adapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (aktualnyKsztalt.pobierzKsztalt() == Ksztalt.Tetromino.Pusty) {
                return;
            }
            int keycode = e.getKeyCode();
            switch (keycode) {
                case KeyEvent.VK_P:
                    pauza();
                    break;
                case KeyEvent.VK_LEFT:
                    sprobujRuszyc(aktualnyKsztalt, aktualnyX - 1, aktualnyY);
                    break;
                case KeyEvent.VK_RIGHT:
                    sprobujRuszyc(aktualnyKsztalt, aktualnyX + 1, aktualnyY);
                    break;
                case KeyEvent.VK_DOWN:
                    jednaLiniaWDol();
                    break;
                case KeyEvent.VK_UP:
                    sprobujRuszyc(aktualnyKsztalt.obrocWLewo(), aktualnyX, aktualnyY);
                    break;
                case KeyEvent.VK_SPACE:
                    upuscNaDol();
                    break;
            }
        }
    }

    /**
     * funkcja czyszczaca cala plansze
     */
    private void wyczyscPlansze() {
        for (int i = 0; i < WYSOKOSC_PLANSZY * SZEROKOSC_PLANSZY; i++) {
            plansza[i] = Ksztalt.Tetromino.Pusty;
        }
    }

    /**
     * funkcja tworzaca nowy klocek na planszy
     */
    private void nowyKlocek() {
        aktualnyKsztalt.ustawRandomKsztalt();
        aktualnyX = SZEROKOSC_PLANSZY / 2 + 1;
        aktualnyY = WYSOKOSC_PLANSZY - 1 + aktualnyKsztalt.minY();

        if (!sprobujRuszyc(aktualnyKsztalt, aktualnyX, aktualnyY)) {

            tab.tablicaWynikow(true, Integer.parseInt(wynik.getText()));
            aktualnyKsztalt.ustawKsztalt(Ksztalt.Tetromino.Pusty);
            timer.stop();

            status.setText("GAME OVER");
        }
    }

    /**
     * Funkcja ustawiajaca poziom (ustawienie klockow na planszy i zmiana predkosci opadania)
     * @param lvl okresla numer poziomu
     */
    private void ustawPoziom(int lvl) throws FileNotFoundException {
        File file = new File("poziom" + lvl + ".txt");
        Scanner in = new Scanner(file);

        while (in.hasNextLine()) {
            aktualnyKsztalt.ustawKonkretnyKsztalt(Integer.parseInt(in.nextLine()));
            aktualnyX = Integer.parseInt(in.nextLine());
            aktualnyY = Integer.parseInt(in.nextLine());
            aktualizacja();
            repaint();
        }
        PREDKOSC=PREDKOSC-50;
        in.close();
    }

    /**
     * funkcja rysyjaca element
     */
    private void rysujElement(Graphics g, int x, int y, Ksztalt.Tetromino shape) {

        Color kolory[] = {new Color(0, 0, 0),
                new Color(Config.redZ, Config.greenZ, Config.blueZ),
                new Color(Config.redS, Config.greenS, Config.blueS),
                new Color(Config.redI, Config.greenI, Config.blueI),
                new Color(Config.redT, Config.greenT, Config.blueT),
                new Color(Config.redO, Config.greenO, Config.blueO),
                new Color(Config.redL, Config.greenL, Config.blueL),
                new Color(Config.redJ, Config.greenJ, Config.blueJ),
                new Color(245, 221, 0),
                new Color(86, 86, 86)
        };

        var kolor = kolory[shape.ordinal()];

        g.setColor(kolor);
        g.fillRect(x + 1, y + 1, szerokoscElementu() - 2, wysokoscElementu() - 2);

        g.setColor(kolor.brighter());
        g.drawLine(x, y + wysokoscElementu() - 1, x, y);
        g.drawLine(x, y, x + szerokoscElementu() - 1, y);

        g.setColor(kolor.darker());
        g.drawLine(x + 1, y + wysokoscElementu() - 1,
                x + szerokoscElementu() - 1, y + wysokoscElementu() - 1);
        g.drawLine(x + szerokoscElementu() - 1, y + wysokoscElementu() - 1,
                x + szerokoscElementu() - 1, y + 1);
    }

}

