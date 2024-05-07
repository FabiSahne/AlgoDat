// O. Bittel;
// 2.8.2023

package aufgabe2.graph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;


/**
 * Klasse zur Analyse von Web-Sites.
 *
 * @author Oliver Bittel
 * @since 30.10.2023
 */
public class AnalyzeWebSite {
    public static void main(String[] args) throws IOException {
        // Graph aus Website erstellen und ausgeben:
        DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("/home/fabian/htwg/AlgoDat/Aufgaben/src/aufgabe2/data/WebSiteKlein");
        //DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("/home/fabian/htwg/AlgoDat/Aufgaben/src/aufgabe2/data/WebSiteGross");
        System.out.println("Anzahl Seiten: \t" + webSiteGraph.getNumberOfVertexes());
        System.out.println("Anzahl Links: \t" + webSiteGraph.getNumberOfEdges());
        //System.out.println(webSiteGraph);

        // Starke Zusammenhangskomponenten berechnen und ausgeben
        StrongComponents<String> sc = new StrongComponents<>(webSiteGraph);
        System.out.println(sc.numberOfComp());
        //System.out.println(sc);

        // Page Rank ermitteln und Top-100 ausgeben
        pageRank(webSiteGraph);
    }

    /**
     * Liest aus dem Verzeichnis dirName alle Web-Seiten und
     * baut aus den Links einen gerichteten Graphen.
     *
     * @param dirName Name eines Verzeichnises
     * @return gerichteter Graph mit Namen der Web-Seiten als Knoten und Links als gerichtete Kanten.
     */
    private static DirectedGraph buildGraphFromWebSite(String dirName) throws IOException {
        File webSite = new File(dirName);
        DirectedGraph<String> webSiteGraph = new AdjacencyListDirectedGraph();

        for (File f : webSite.listFiles()) {
            String from = f.getName();
            LineNumberReader in = new LineNumberReader(new FileReader(f));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("href")) {
                    String[] s_arr = line.split("\"");
                    String to = s_arr[1];
                    webSiteGraph.addEdge(from, to);
                }
            }
        }
        return webSiteGraph;
    }

    /**
     * pageRank ermittelt Gewichte (Ranks) von Web-Seiten
     * aufgrund ihrer Link-Struktur und gibt sie aus.
     *
     * @param g gerichteter Graph mit Web-Seiten als Knoten und Links als Kanten.
     */
    private static <V> void pageRank(DirectedGraph<V> g) {
        int nI = 10;
        double alpha = 0.5;

        // Definiere und initialisiere rankTable:
        double[] rankTable = new double[g.getNumberOfVertexes()];
        Arrays.fill(rankTable, 1);

        // Iteration:
        for (int i = 0; i < nI; i++) {
            double[] newRankTable = new double[g.getNumberOfVertexes()];
            ListIterator<V> v = g.getVertexSet().stream().toList().listIterator();
            while (v.hasNext()) {
                double rank = 0;
                ListIterator<V> w = g.getPredecessorVertexSet(v.next()).stream().toList().listIterator();
                while (w.hasNext()) {
                    rank += rankTable[w.nextIndex()] / g.getSuccessorVertexSet(w.next()).size();
                }
                newRankTable[v.nextIndex()] = (alpha * rank + (1 - alpha));
                v.next();
            }
            rankTable = newRankTable;
        }

        // Rank Table ausgeben (nur für data/WebSiteKlein):
        if (g.getNumberOfVertexes() < 100) {
            System.out.println("\nRanktabelle:");
            for (int i = 0; i < rankTable.length; i++) {
                System.out.println("Page: "+ i + ",\t Rank: " + rankTable[i]);
            }
        }

        // Nach Ranks sortieren Top 100 ausgeben (nur für data/WebSiteGross):
        else {
            System.out.println("\nTop 100:");
            TreeMap<Double, Integer> sortedRankTable = new TreeMap<>();
            for (int i = 0; i < rankTable.length; i++) {
                sortedRankTable.put(rankTable[i], i);
            }
            int count = 0;
            for (Map.Entry<Double, Integer> entry : sortedRankTable.descendingMap().entrySet()) {
                if (count < 100) {
                    System.out.println("Page: " + entry.getValue() + ",\t Rank: " + entry.getKey());
                    count++;
                } else {
                    break;
                }
            }
        }
        
        // Top-Seite mit ihren Vorgängern und Ranks ausgeben (nur für data/WebSiteGross):
        if (g.getNumberOfVertexes() > 100) {
            System.out.println("\nTop-Seite:");
            double maxRank = 0;
            int maxRankIndex = 0;
            for (int i = 0; i < rankTable.length; i++) {
                if (rankTable[i] > maxRank) {
                    maxRank = rankTable[i];
                    maxRankIndex = i;
                }
            }
            System.out.println(maxRankIndex + " mit Rank: " + maxRank);
            System.out.println("Vorgänger: ");
            for (V v : g.getPredecessorVertexSet((V) g.getVertexSet().toArray()[maxRankIndex])) {
                System.out.println(v + " mit Rank: " + rankTable[Integer.parseInt(v.toString().split("_")[1].split("\\.")[0])]);
            }
        }
        
    }
}
