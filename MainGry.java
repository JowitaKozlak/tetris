import java.awt.*;
/**
 * Klasa z funkcja main
 */
public class MainGry {
    /**
     * Funkcja main
     */
    public static void main(String[] args) {
        /**
         * Utworzenie okna menu
         */
        final OknoMenu menu = new OknoMenu();
        /**
         * Utworzenie obiektu klasy "Config", służącego do wczytania pliku parametrycznego
         */
        final Config config = new Config();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                menu.setVisible(true);
                menu.launchFrame();
            }
        });
    }
}
