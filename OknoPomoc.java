import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * Klasa odpowiadajaca za okno pomocy
 */
public class OknoPomoc extends Frame {
    /***
     * Konstruktor, okreslajacy nazwe okna
     */
    public OknoPomoc() {super("Pomoc"); }
    /**
     * Uruchomienie okna pomocy
     */
    public void launchFrame() {
        /**
         * Sluchacz zdarzen obslugujacy zdarzenie zamkniecia okna
         */
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                return;
            }
        });
        /**
         * Pole tekstowe bez mozliwosci edycji
         */
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.append(Config.pomoc);
        add(textArea);
        /**
         * Ustawienie rozmiaru okna
         */
        setSize(Config.szerokoscPomoc, Config.wysokoscPomoc);
    }
}
