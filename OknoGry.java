import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa dziedzicząca po klasie OknoMenu odpowiadajaca za okno gry
 */
public class OknoGry extends OknoMenu {
    /**
     * Etykieta zawierajaca status gry
     */
    private Label status;
    /**
     * Konstruktor klasy wywolujacy funkcje wyswitlajaca okno
     */
    private Button powrot = null;
    public OknoGry() {
        launchFrame();
    }

    /**
     * funkcja tworzaca i wyswietlajaca okno gry
     */
    public void launchFrame() {
        super.launchFrame();
        tetrion.removeAll();
        info.remove(pomoc);
        info.remove(zakoncz);
        status = new Label("");
        info.add(powrot = new Button("Powrot do menu" ));
        info.add(status);

        var plansza = new Plansza(this);
        tetrion.add(plansza);
        plansza.start();

        setTitle("Tetris");
        plansza.setSize(Config.szerokoscTetrion, Config.wysokoscTetrion);
        setLocationRelativeTo(null);
        /**
         * Sluchacz zdarzen realizujacy powrot do menu glownego (bez zapisania wyniku)
         */
        powrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OknoMenu menu = new OknoMenu();
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        menu.setVisible(true);
                        menu.launchFrame();
                    }
                });
                dispose();
                return;
            }
        });

    }
    /**
     * Funkcja zwracjaca status gry
     */
    Label pobierzStatus(){
        return status;
    }
    /**
     * Funkcja zwracajaca informacje o liczbie (pelnych) ulozonych przez gracza linii
     */
    Label pobierzLinie(){
        return lines;
    }
    /**
     * Funkcja zwracjaca informacje o liczbie punktow zdpbytych przez gracza
     */
    Label pobierzWynik(){
        return score;
    }
    /**
     * Funkcja zwracajaca informacje o poziomie, na ktorym znajduje sie uzytkownik
     */
    Label pobierzPoziom(){
        return lvl;
    }
}
