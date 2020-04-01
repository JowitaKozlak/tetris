import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Klasa odpowiadajaca za okno menu glownego gry
 */
public class OknoMenu extends Frame {
    /**
     * Konstruktor okreslajacy nazwe okna
     */
    public OknoMenu() {super("Tetris - Menu Glowne");}
    /**
     * Przycisk odpowiadajacy za start nowej rogrywki
     */
    Button start = null;
    /**
     * Przycisk odpowiadajacy za wyjscie z gry
     */
    Button zakoncz = null;
    /**
     * Przycisk odpowiadajacy za otworzenie okna pomoc
     */
    Button pomoc = null;
    /**
     * Przycisk odpowiadajacy za zresetownie tablicy wynikow
     */
    Button resetListy = null;
    /**
     * Etykieta zawierajaca numer poziomu
     */
    Label poziom = null;
    /**
     * Etykieta zawierajaca wynik gracza
     */
    Label wynik = null;
    /**
     * Etykieta zawierajaca liczbe ulozonych (zbitych) linii
     */
    Label linie = null;
    /**
     * Etykieta zawierajaca naglowek tablicy
     */
    Label naglowekTablicy = null;
    /**
     * Panel z przyciskiem "Start" i tablica wynikow
     */
    Panel tetrion = new Panel(new BorderLayout());
    /**
     * Panel z etykietami "Poziom", "Wynik", "Linie" oraz przyciskami "Pauza", "Zakoncz", "Pomoc"
     */
    Panel info = new Panel();
    /**
     * Panel z tablica wynikow
     */
    Panel tablicaWynikow = new Panel();
    /**
     * Etykieta zawierajaca informacje o poziomie, na ktorym znajduje sie uzytkownik
     */
    Label lvl=null;
    /**
     * Etykieta zawierajaca informacje o liczbie punktow zdpbytych przez gracza
     */
    Label score=null;
    /**
     * Etykieta zawierajaca informacje o licznie ulozonych przez gracza linii
     */
    Label lines=null;
    /**
     * Etykieta przechowujaca najlepszy wynik
     */
    Label wynik1=null;
    /**
     * Etykieta przechowujaca drugi wynik
     */
    Label wynik2=null;
    /**
     * Etykieta przechowujaca trzeci wynik
     */
    Label wynik3=null;
    /**
     * Etykieta przechowujaca czwarty wynik
     */
    Label wynik4=null;
    /**
     * Etykieta przechowujaca najgorszy wynik
     */
    Label wynik5=null;
    /**
     * Uruchomienie okna menu
     */
    public void launchFrame() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(1);
            }
        });

        setSize(Config.szerokoscOkna,Config.wysokoscOkna);

        GridLayout gridInfo = new GridLayout(8,1);
        info.setLayout(gridInfo);
        tetrion.setBackground(new java.awt.Color(Config.red,Config.green,Config.blue));
        tetrion.add(start = new Button("Start"), BorderLayout.NORTH);
        start.setPreferredSize(new Dimension(100,200));
        tetrion.add(tablicaWynikow = new Panel(), BorderLayout.CENTER);
        tetrion.add(resetListy = new Button("Reset Listy Wynikow"), BorderLayout.SOUTH);
        add(tetrion);

        info.add(poziom = new Label("Poziom: "));
        info.add(lvl = new Label("1"));
        info.add(wynik = new Label("Wynik: "));
        info.add(score = new Label("0"));
        info.add(linie = new Label("Linie: "));
        info.add(lines = new Label("0"));
        info.add(pomoc = new Button("Pomoc" ));
        info.add(zakoncz = new Button("Zakoncz"));
        add(info, BorderLayout.EAST);
        info.setBackground(new java.awt.Color(Config.red,Config.green,Config.blue));
        info.setPreferredSize(new Dimension(Config.szerokoscInfo, Config.wysokoscInfo));

        GridLayout gridTablicaWynikow = new GridLayout(6,1);
        tablicaWynikow.setLayout(gridTablicaWynikow);
        tablicaWynikow.add(naglowekTablicy = new Label("Najlepsze wyniki:"));
        tablicaWynikow.setPreferredSize(new Dimension(100,100)); //config?
        tablicaWynikow.setBackground(new java.awt.Color(Config.red,Config.green,Config.blue));

        TablicaWynikow tab = new TablicaWynikow();
        tab.odczytZPliku();
        tablicaWynikow.add(wynik1 = new Label("1. " + tab.tablicaPunktow[0]));
        tablicaWynikow.add(wynik2 = new Label("2. " + tab.tablicaPunktow[1]));
        tablicaWynikow.add(wynik3 = new Label("3. " + tab.tablicaPunktow[2]));
        tablicaWynikow.add(wynik4 = new Label("4. " + tab.tablicaPunktow[3]));
        tablicaWynikow.add(wynik5 = new Label("5. " + tab.tablicaPunktow[4]));

        /**
         * Sluchacz zdarzen obslugujacy zdarzenie nacisniecia przycisku "Start"
         */
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OknoGry gra = new OknoGry();
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        gra.setVisible(true);
                    }
                });
                dispose();
                return;
            }
        });

        /**
         * Sluchacz zdarzen obslugujacy zdarzenie nacisniecia przycisku "Zakoncz"
         */
        zakoncz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        /**
         * Sluchacz zdarzen obslugujacy zdarzenie nacisniecia przycisku "Pomoc"
         */
        pomoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OknoPomoc pom = new OknoPomoc();
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        pom.setVisible(true);
                        pom.launchFrame();
                    }
                });
                //dispose();
                return;
            }
        });
        /**
         * Sluchacz zdarzen obslugujacy zdarzenie nacisniecia przycisku "Reset listy"
         * - resetuje tablice wynikow i ustawia wartosci na 0 (takze w pliku)
         */
        resetListy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    FileWriter fw = new FileWriter("listawynikow.txt");
                    PrintWriter pw = new PrintWriter(fw);
                    for (int i=0;i<5;i++)
                    {
                        pw.println(0);
                    }
                    pw.close();
                }
                catch(IOException ie)
                {
                    System.out.println("Błąd zapisu do pliku");
                }
                try
                {
                    FileReader fr = new FileReader("listawynikow.txt");
                    BufferedReader br = new BufferedReader(fr);
                    int punktydozapisu;
                    for(int i=0;i<5;i++)
                    {
                        punktydozapisu=Integer.parseInt(br.readLine());
                        tab.tablicaPunktow[i]=punktydozapisu;
                    }
                    br.close();
                }
                catch (IOException ie)
                {
                    System.out.println("Bład odczytu z pliku");
                }
                wynik1.setText("1: " + tab.tablicaPunktow[0]);
                wynik2.setText("2: " + tab.tablicaPunktow[1]);
                wynik3.setText("3: " + tab.tablicaPunktow[2]);
                wynik4.setText("4: " + tab.tablicaPunktow[3]);
                wynik5.setText("5: " + tab.tablicaPunktow[4]);
            }
        });

        EventQueue.invokeLater(() -> setVisible(true));
    }
}
