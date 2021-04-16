package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static String plik = "src/main/java/pl/coderslab/tasks.csv";
    static final String[] opcjeProgramu = {"dodaj", "usuń", "pokaż listę", "wyjdź"};
    static String[][] zadaniaPlik;

    public static void main(String[] args) {
        wczytajDanePlikuDoTablicy(plik);
        wyswietlOpcje(opcjeProgramu);

        Scanner uzytkownik = new Scanner(System.in);
        String decyzja = uzytkownik.nextLine();
        while(uzytkownik.hasNext()){
            decyzja = uzytkownik.nextLine();
        }

        switch (decyzja) {
            case "dodaj":
                dodajZadanie();
                break;
            case "usuń":

                break;

            case "pokaż listę":

                break;

            case "wyjdź":

                break;

            default:
                System.out.println("Wybierz poprawną opcję");

        }
    }

    public static void wyswietlOpcje(String[] tablica) {
        System.out.println(ConsoleColors.BLUE + "Proszę wybierz jedną z opcji:" + ConsoleColors.RESET);
        for (String opcja : tablica
        ) {
            System.out.println(opcja);
        }
    }

    public static void dodajZadanie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj opis zadania: ");
        String opisZadania = scanner.nextLine();
        System.out.println("Podaj datę wykonania zadania: ");
        String dataWykonania = scanner.nextLine();
        System.out.println("Czy to zadanie jest ważne?tak/nie");
        String istotnoscZadania = scanner.nextLine();

        zadaniaPlik = Arrays.copyOf(zadaniaPlik, zadaniaPlik.length + 1);


    }


    public static String[][] wczytajDanePlikuDoTablicy(String nazwaPliku) {
        Path naszPlik = Paths.get(nazwaPliku);
        if (!Files.exists(naszPlik)) {
            System.out.println("Plik nie istnieje.");
            System.exit(0);
        }
        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(naszPlik);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }


}
