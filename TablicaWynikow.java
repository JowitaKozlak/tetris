import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TablicaWynikow extends Frame{
    /**
     * Zmienna okreslajaca, czy bedziemy zapisywac nowy wynik, czy tylko otwierac liste, aby przejrzec istiejace wyniki
     */
    public boolean czyZapisujemy;
    /**
     * Zmienna, w ktorej przechowywana jest liczba punktow gracza, który aktualnie przegral lub wygral gre
     */
    public int nowyWynikDoZapisu;
    /**
     * Tablica przechowujaca liste 5 najlepszych punktow wynikow
     */
    int[] tablicaPunktow=new int[5];
    /**
     * Konstruktor ustawiajacy wartosci tablicy na 0
     */
    public TablicaWynikow(){
        for(int i=0; i<5; i++)
        tablicaPunktow[i]=0;
    }
    /**
     * Funkcja dodajaca w odpowiednim miejscu wynik, tak, aby lista była posortowana malejaco
     * oraz zapisujaca tablice do pliku
     */
    protected void tablicaWynikow(boolean czyZapis, int pkt) {
        czyZapisujemy = czyZapis;
        nowyWynikDoZapisu = pkt;
        odczytZPliku();

        if(czyZapis==true) {
            if (tablicaPunktow[4] < nowyWynikDoZapisu) {
                for (int i = 0; i < 5; i++) {
                    if (nowyWynikDoZapisu > tablicaPunktow[i]) {
                        for (int j = 4; j > i; j--) {
                            tablicaPunktow[j] = tablicaPunktow[j - 1];
                        }
                        tablicaPunktow[i] = nowyWynikDoZapisu;
                        break;
                    }
                }
            }
            try
            {
                FileWriter fw = new FileWriter("listawynikow.txt");
                PrintWriter pw = new PrintWriter(fw);
                for (int i=0;i<5;i++)
                {
                    pw.println(tablicaPunktow[i]);
                }
                pw.close();
            }
            catch(IOException e)
            {
                System.out.println("Błąd zapisu do pliku");
            }
        }
    }
    /**
     * Funkcja wczytujaca liste wynikow z pliku do tablicy z punktami
     */
    protected void odczytZPliku(){
        try
        {
            FileReader fr = new FileReader("listawynikow.txt");
            BufferedReader br = new BufferedReader(fr);
            int punktydozapisu;
            for(int i=0;i<5;i++)
            {
                punktydozapisu=Integer.parseInt(br.readLine());
                tablicaPunktow[i]=punktydozapisu;
            }
            br.close();
        }
        catch (IOException e)
        {
            System.out.println("Błąd odczytu z pliku");
        }
    }
}