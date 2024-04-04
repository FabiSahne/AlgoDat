package dictionary;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Laufzeitmessung {

    static File file = null;

    public static void main(String[] args) throws FileNotFoundException {

        file = new File("dictionary/dtengl.txt");

        // test SortedArrayDictionary
        System.out.println("Test SortedArrayDictionary:");
        Dictionary<String, String> sortedArrayDictionary = new SortedArrayDictionary<>();
        testDict(sortedArrayDictionary);
        // test HashDictionary
        System.out.println("Test HashDictionary:");
        Dictionary<String, String> hashDictionary = new HashDictionary<>(11);
        testDict(hashDictionary);
        // test BinaryTreeDictionary
        System.out.println("Test BinaryTreeDictionary:");
        Dictionary<String, String> binaryTreeDictionary = new BinaryTreeDictionary<>();
        testDict(binaryTreeDictionary);
    }

    private static void testDict(Dictionary<String, String> dict) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        // insert
        System.out.println("Insert:");
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            dict.insert(parts[0], parts[1]);
            i++;
            if (i == 8000) {
                long time8000 = System.currentTimeMillis() - startTime;
                System.out.println("  8000: " + time8000 + " ms");
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("~16000: " + endTime + " ms");
        // search german words
        List<String> germanWords = new ArrayList<>();
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            germanWords.add(parts[0]);
        }
        System.out.println("Search german:");
        startTime = System.currentTimeMillis();
        i = 0;
        for (String word : germanWords) {
            dict.search(word);
            i++;
            if (i == 8000) {
                long time8000 = System.currentTimeMillis() - startTime;
                System.out.println("  8000: " + time8000 + " ms");
            }
        }
        endTime = System.currentTimeMillis() - startTime;
        System.out.println("~16000: " + endTime + " ms");
        // search english words
        List<String> englishWords = new ArrayList<>();
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            englishWords.add(parts[1]);
        }
        System.out.println("Search english:");
        startTime = System.currentTimeMillis();
        i = 0;
        for (String word : englishWords) {
            dict.search(word);
            i++;
            if (i == 8000) {
                long time8000 = System.currentTimeMillis() - startTime;
                System.out.println("  8000: " + time8000 + " ms");
            }
        }
        endTime = System.currentTimeMillis() - startTime;
        System.out.println("~16000: " + endTime + " ms");
    }
}
