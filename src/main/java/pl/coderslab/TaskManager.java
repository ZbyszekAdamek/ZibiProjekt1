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
        zadaniaPlik = wczytajDanePlikuDoTablicy(plik);
        wyswietlOpcje(opcjeProgramu);

        Scanner uzytkownik = new Scanner(System.in);
        while (uzytkownik.hasNext()) {
            String decyzja = uzytkownik.nextLine();

            switch (decyzja) {

                case "pokaż listę":
                    pokazListeZadan(zadaniaPlik);
                    break;

                case "dodaj":
                    dodajZadanie();
                    System.out.println("Zadanie zostałe dodane do listy.");
                    break;

                case "usuń":
                    usunZadanie(zadaniaPlik, wybierzLiczbe());
                    System.out.println("Wartość została poprawnie usunięta.");
                    break;

                case "wyjdź":
                    zapiszDaneDoPliku(zadaniaPlik, plik);
                    System.out.println(ConsoleColors.RED + "Pa, pa");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Wybierz poprawną opcję");
            }
            wyswietlOpcje(opcjeProgramu);
        }
    }

    public static void wyswietlOpcje(String[] tablica) {
        System.out.println(ConsoleColors.BLUE + "Proszę wybierz jedną z opcji:" + ConsoleColors.RESET);
        for (String opcja : tablica
        ) {
            System.out.println(opcja);
        }
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

    public static void pokazListeZadan(String[][] tablica) {
        for (int i = 0; i < tablica.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < tablica[i].length; j++) {
                System.out.print(tablica[i][j] + " ");
            }
            System.out.println();
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

        zadaniaPlik[zadaniaPlik.length - 1] = new String[3];
        zadaniaPlik[zadaniaPlik.length - 1][0] = opisZadania;
        zadaniaPlik[zadaniaPlik.length - 1][1] = dataWykonania;
        zadaniaPlik[zadaniaPlik.length - 1][2] = istotnoscZadania;
    }

    public static void usunZadanie(String[][] tablicaDwuwymiarowa, int index) {
        try {
            if (index < tablicaDwuwymiarowa.length) {
                zadaniaPlik = ArrayUtils.remove(tablicaDwuwymiarowa, index);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Indeks wyszedł poza tablicę");
        }
    }

    public static int wybierzLiczbe() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz liczbę do usunięcia");
        String wybor = scanner.nextLine();
        if (!jestWiekszeLubRowneZero(wybor)) {
            System.out.println("Popraw wartość na większą lub równą zero.");
            scanner.nextLine();
        }
        return Integer.parseInt(wybor);

    }

    public static boolean jestWiekszeLubRowneZero(String decyzja) {
        if (NumberUtils.isParsable(decyzja)) {
            return Integer.parseInt(decyzja) >= 0;
        }
        return false;
    }

    public static void zapiszDaneDoPliku(String[][] tablicaDwuwymiarowa, String plik) {
        Path dir = Paths.get(plik);

        String[] lines = new String[zadaniaPlik.length];
        for (int i = 0; i < zadaniaPlik.length; i++) {
            lines[i] = String.join(",", tablicaDwuwymiarowa[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


