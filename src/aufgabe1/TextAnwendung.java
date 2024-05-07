package aufgabe1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextAnwendung {

    public static void main(String[] args) throws FileNotFoundException {
        Dictionary<String, String> dictionary = null;
        while (true) {
            System.out.print("Bitte geben Sie einen Befehl ein. <help> um Befehle anzuzeigen.\n\uE0B0 ");
            String input = new java.util.Scanner(System.in).nextLine();
            if (input.equals("help")) {
                System.out.println("Befehle:\n\t<help> - zeigt diese Hilfe an\n\t<create Implementierung> - Legt ein Wörterbuch an.\n\t<exit> - beendet das Programm\n\t<i deutsch englisch> - fuegt einen Eintrag hinzu\n\t<d deutsch> - loescht einen Eintrag\n\t<s deutsch> - sucht einen Eintrag\n\t<p> - zeigt alle Eintraege an");
            } else if (input.equals("exit")) {
                break;
            } else if (input.startsWith("i")) {
                if (dictionary == null) {
                    System.out.println("Bitte erstellen Sie ein Wörterbuch.");
                    continue;
                }
                String[] parts = input.split(" ");
                if (parts.length == 3) {
                    String deutsch = parts[1];
                    String englisch = parts[2];
                    // Eintrag hinzufuegen
                    dictionary.insert(deutsch, englisch);
                    System.out.println("Eintrag hinzugefuegt: " + deutsch + " - " + englisch);
                } else {
                    System.out.println("Bitte geben Sie ein deutsches und ein englisches Wort an.");
                }
            } else if (input.startsWith("create ") || input.equals("create")) {
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    if (parts[1].equals("HashDictionary")) {
                        dictionary = new HashDictionary<>(11);
                        System.out.println("HashDictionary erstellt.");
                    } else if (parts[1].equals("BinaryTreeDictionary")) {
                        dictionary = new BinaryTreeDictionary<>();
                        System.out.println("BinaryTreeDictionary erstellt.");
                    } else if (parts[1].equals("SortedArrayDictionary")) {
                        dictionary = new SortedArrayDictionary<>();
                        System.out.println("SortedArrayDictionary erstellt.");
                    } else {
                        System.out.println("Unbekannte Implementierung.");
                    }
                } else {
                    dictionary = new SortedArrayDictionary<>();
                    System.out.println("SortedArrayDictionary erstellt.");
                }
            } else if (input.startsWith("d")) {
                if (dictionary == null) {
                    System.out.println("Bitte erstellen Sie ein Wörterbuch.");
                    continue;
                }
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    String deutsch = parts[1];
                    // Eintrag loeschen
                    String englisch = dictionary.remove(deutsch);
                    if (englisch != null) {
                        System.out.println("Eintrag gelöscht: " + deutsch + " - " + englisch);
                    } else {
                        System.out.println("Eintrag nicht gefunden: " + deutsch);
                    }
                } else {
                    System.out.println("Bitte geben Sie ein deutsches Wort an.");
                }
            } else if (input.startsWith("s")) {
                if (dictionary == null) {
                    System.out.println("Bitte erstellen Sie ein Wörterbuch.");
                    continue;
                }
                String[] parts = input.split(" ");
                if (parts.length == 2) {
                    String deutsch = parts[1];
                    // Eintrag suchen
                    String englisch = dictionary.search(deutsch);
                    if (englisch != null) {
                        System.out.println("Eintrag gefunden: " + deutsch + " - " + englisch);
                    } else {
                        System.out.println("Eintrag nicht gefunden: " + deutsch);
                    }
                } else {
                    System.out.println("Bitte geben Sie ein deutsches Wort an.");
                }
            } else if (input.equals("p")) {
                if (dictionary == null) {
                    System.out.println("Bitte erstellen Sie ein Wörterbuch.");
                    continue;
                }
                // Alle Eintraege anzeigen
                System.out.println("Alle Eintraege:");
                for (Dictionary.Entry<String, String> entry : dictionary) {
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
            } else if (input.startsWith("r")) {
                if (dictionary == null) {
                    System.out.println("Bitte erstellen Sie ein Wörterbuch.");
                    continue;
                }
                String[] parts = input.split(" ");
                if (parts.length == 3) {
                    int n = Integer.parseInt(parts[1]);
                    File f = new File(parts[2]);
                    Scanner scanner = new Scanner(f);
                    for (int i = 0; i < n; i++) {
                        if (!scanner.hasNextLine()) {
                            break;
                        }
                        String line = scanner.nextLine();
                        String[] words = line.split(" ");
                        dictionary.insert(words[0], words[1]);
                    }
                } else {
                    File f = new File(parts[1]);
                    Scanner scanner = new Scanner(f);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] words = line.split(" ");
                        dictionary.insert(words[0], words[1]);
                    }
                }
            }
        }
    }
}
