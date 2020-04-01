import java.io.*;
import java.util.Properties;
/**
 * Klasa sluzaca do wczytania pliku parametrycznego
 */
public class Config{
    /**
     * Zmienna publiczna statyczna typu int przechowujaca szerokosc okna menu
     */
    public static int szerokoscOkna;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wysokosc okna menu
     */
    public static int wysokoscOkna;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca szerokosc panelu informacyjnego
     */
    public static int szerokoscInfo;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wysokosc panelu informacyjnego
     */
    public static int wysokoscInfo;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca szerokosc panelu panelu tetrion
     */
    public static int szerokoscTetrion;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wysokosc panelu tetrion
     */
    public static int wysokoscTetrion;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc poczatkowa
     */
    public static int initialValue;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca szerokosc okna pomoc
     */
    public static int szerokoscPomoc;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wysokosc okna pomoc
     */
    public static int wysokoscPomoc;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" koloru tła
     */
    public static int red;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" koloru tła
     */
    public static int green;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" koloru tła
     */
    public static int blue;
    /**
     * Zmienna publiczna statyczna typu String przechowujaca tekst wyswietlany po nacisnieciu przycisku "pomoc".
     */
    public static String pomoc;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" klocka Z
     */
    public static int redZ;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" klocka Z
     */
    public static int greenZ;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" klocka Z
     */
    public static int blueZ;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" klocka S
     */
    public static int redS;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" klocka S
     */
    public static int greenS;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" klocka S
     */
    public static int blueS;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" klocka I
     */
    public static int redI;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" klocka I
     */
    public static int greenI;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" klocka I
     */
    public static int blueI;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" klocka T
     */
    public static int redT;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" klocka T
     */
    public static int greenT;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" klocka T
     */
    public static int blueT;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" klocka O
     */
    public static int redO;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" klocka O
     */
    public static int greenO;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" klocka O
     */
    public static int blueO;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" klocka L
     */
    public static int redL;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" klocka L
     */
    public static int greenL;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" klocka L
     */
    public static int blueL;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "r" klocka J
     */
    public static int redJ;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "g" klocka J
     */
    public static int greenJ;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc skladowej  "b" klocka J
     */
    public static int blueJ;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca liczbe dostepnych poziomow
     */
    public static int liczbaPoziomow;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca wartosc predkosci opadania klocka
     */
    public static int predkosc;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca informacje co ile punktow nastepuje zmiana poziomu
     */
    public static int progPunktowy;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca procentowa wartosc prawdopodobienstwa generacji klockow bonusowych
     */
    public static int pstwoBonusow;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca informacje o liczbie kolumn Tetrionu
     */
    public static int liczbaKolumn;
    /**
     * Zmienna publiczna statyczna typu int przechowujaca informacje o liczbie wierszy Tetrionu
     */
    public static int liczbaWierszy;

    /**
     * Konstruktor klasy zawierajacy pozwalajacy wczytac dane z pliku paramtrycznego
     */
    Config() {
        Properties props = new Properties();

        try (Reader in = new InputStreamReader(new FileInputStream("config.txt"))) {
            props.load(in);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        /**
        * parsowanie parametrow
        */
        szerokoscOkna=Integer.parseInt(props.getProperty("szerokoscOkna"));
        wysokoscOkna=Integer.parseInt(props.getProperty("wysokoscOkna"));
        szerokoscInfo=Integer.parseInt(props.getProperty("szerokoscInfo"));
        wysokoscInfo=Integer.parseInt(props.getProperty("wysokoscInfo"));
        szerokoscTetrion=Integer.parseInt(props.getProperty("szerokoscTetrion"));
        wysokoscTetrion=Integer.parseInt(props.getProperty("wysokoscTetrion"));
        initialValue=Integer.parseInt(props.getProperty("initialValue")); //nie uzywany!!! do usuniecia
        szerokoscPomoc=Integer.parseInt(props.getProperty("szerokoscPomoc"));
        wysokoscPomoc=Integer.parseInt(props.getProperty("wysokoscPomoc"));
        red=Integer.parseInt(props.getProperty("red"));
        green=Integer.parseInt(props.getProperty("green"));
        blue=Integer.parseInt(props.getProperty("blue"));
        pomoc=props.getProperty("tekst");

        redZ=Integer.parseInt(props.getProperty("redZ"));
        greenZ=Integer.parseInt(props.getProperty("greenZ"));
        blueZ=Integer.parseInt(props.getProperty("blueZ"));

        redS=Integer.parseInt(props.getProperty("redS"));
        greenS=Integer.parseInt(props.getProperty("greenS"));
        blueS=Integer.parseInt(props.getProperty("blueS"));

        redI=Integer.parseInt(props.getProperty("redI"));
        greenI=Integer.parseInt(props.getProperty("greenI"));
        blueI=Integer.parseInt(props.getProperty("blueI"));

        redT=Integer.parseInt(props.getProperty("redT"));
        greenT=Integer.parseInt(props.getProperty("greenT"));
        blueT=Integer.parseInt(props.getProperty("blueT"));

        redO=Integer.parseInt(props.getProperty("redO"));
        greenO=Integer.parseInt(props.getProperty("greenO"));
        blueO=Integer.parseInt(props.getProperty("blueO"));

        redL=Integer.parseInt(props.getProperty("redL"));
        greenL=Integer.parseInt(props.getProperty("greenL"));
        blueL=Integer.parseInt(props.getProperty("blueL"));

        redJ=Integer.parseInt(props.getProperty("redJ"));
        greenJ=Integer.parseInt(props.getProperty("greenJ"));
        blueJ=Integer.parseInt(props.getProperty("blueJ"));

        liczbaPoziomow=Integer.parseInt(props.getProperty("liczbaPoziomow"));
        predkosc = Integer.parseInt(props.getProperty("predkosc"));
        progPunktowy=Integer.parseInt(props.getProperty("progPunktowy"));
        pstwoBonusow=Integer.parseInt(props.getProperty("pstwoBonusow"));
        liczbaKolumn=Integer.parseInt(props.getProperty("liczbaKolumn"));
        liczbaWierszy=Integer.parseInt(props.getProperty("liczbaWierszy"));
    }
}