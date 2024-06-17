package aufgabe4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
Klasse zur Verwalten von Telefonknoten mit (x,y)-Koordinaten und zur Berechnung eines minimal
aufspannenden Baums mit dem Algorithmus von Kruskal.
Kantengewichte sind durch den Manhattan-Abstand definiert.
 */
public class TelNet {

    int size;
    int lbg;
    List<TelKnoten> telKnoten;
    List<TelVerbindung> telVerbindung;

    /**
     * Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
     * @param lbg Leitungsbegrenzungswert
     */
    public TelNet(int lbg) {
        size = 0;
        this.lbg = lbg;
        telKnoten = new ArrayList<>();
        telVerbindung = new ArrayList<>();
    }

    /**
     * Fuegt einen neuen Telefonknoten mit den Koordinaten (x,y) hinzu.
     * @param x x-Koordinate
     * @param y y-Koordinate
     * @return true, wenn der Knoten hinzugefuegt wurde, sonst false
     */
    public boolean addTelConnection(int x, int y) {
        if (telKnoten.contains(new TelKnoten(x, y)) || telKnoten.size() == lbg) {
            return false;
        }
        telKnoten.add(new TelKnoten(x, y));
        size++;
        return true;
    }

    /**
     * Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
     * @return true, wenn ein optimales Telefonnetz berechnet wurde, sonst false
     */
    public boolean computeOptTelNet() {
        if (size == 0) {
            return false;
        }
        telVerbindung.clear();

        PriorityQueue<TelVerbindung> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.c));
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                TelKnoten u = telKnoten.get(i);
                TelKnoten v = telKnoten.get(j);
                int c = Math.abs(u.x - v.x) + Math.abs(u.y - v.y);
                pq.add(new TelVerbindung(u, v, c));
            }
        }

        UnionFind uf = new UnionFind(size);
        while (!pq.isEmpty()) {
            TelVerbindung tv = pq.poll();
            int u = telKnoten.indexOf(tv.u);
            int v = telKnoten.indexOf(tv.v);
            if (uf.find(u) != uf.find(v)) {
                uf.union(u, v);
                telVerbindung.add(tv);
            }
        }
        return true;
    }

    /**
     * Zeichnet ein optimales Telefonnetz in einem Fenster der Groesse xMax x yMax.
     * @param xMax Maximale x-Größe
     * @param yMax Maximale y-Größe
     * @throws IllegalStateException, falls nicht zuvor <code>computeOptTelNet()</code> aufgerufen wurde.
     */
    public void drawOptTelNet(int xMax, int yMax) throws IllegalStateException {
        if (size == 0) {
            throw new IllegalStateException("computeOptTelNet() must be called first");
        }
        int xScale = xMax / telKnoten.stream().mapToInt(o -> o.x).max().orElse(0);
        int yScale = yMax / telKnoten.stream().mapToInt(o -> o.y).max().orElse(0);
        StdDraw.setCanvasSize(xMax, yMax);
        StdDraw.setXscale(0, xMax);
        StdDraw.setYscale(0, yMax);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (TelVerbindung tv : telVerbindung) {
            StdDraw.line(tv.u.x * xScale, tv.u.y * yScale, tv.v.x * xScale, tv.u.y * yScale);
            StdDraw.line(tv.v.x * xScale, tv.u.y * yScale, tv.v.x * xScale, tv.v.y * yScale);
        }
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        for (TelKnoten tk : telKnoten) {
            StdDraw.filledRectangle(tk.x * xScale, tk.y * yScale, 2, 2);
        }
    }

    /**
     * Generiert n zufaellige Telefonknoten mit x- und y-Koordinaten im Bereich [0,xMax] bzw. [0,yMax].
     * @param n Anzahl der Telefonknoten
     * @param xMax Maximale x-Größe
     * @param yMax Maximale y-Größe
     */
    public void generateRandomTelNet(int n, int xMax, int yMax) {
        for (int i = 0; i < n; i++) {
            addTelConnection((int) (Math.random() * xMax), (int) (Math.random() * yMax));
        }
    }

    /**
     * Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen.
     * @return Liste von Telefonverbindungen.
     * @throws IllegalStateException, falls nicht zuvor <code>computeOptTelNet()</code> aufgerufen wurde.
     */
    public List<TelVerbindung> getOptTelNet() throws IllegalStateException {
        if (size == 0) {
            throw new IllegalStateException("computeOptTelNet() must be called first");
        }
        return telVerbindung;
    }

    /**
     * Liefert die Gesamtkosten eines optimalen Telefonnetzes.
     * @return Gesamtkosten eines optimalen Telefonnetzes.
     * @throws IllegalStateException, falls nicht zuvor <code>computeOptTelNet()</code> aufgerufen wurde.
     */
    public int getOptTelNetKosten() throws IllegalStateException {
        if (size == 0) {
            throw new IllegalStateException("computeOptTelNet() must be called first");
        }
        int sum = 0;
        for (TelVerbindung tv : telVerbindung) {
            sum += tv.c;
        }
        return sum;
    }

    /**
     * Liefert die Anzahl der Telefonknoten.
     * @return Anzahl der Telefonknoten.
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * Anwendung.
     * @param args - wird nicht verwendet.
     */
    public static void main(String[] args) {
        // Beispiel aus der Aufgabenstellung
        TelNet tn = new TelNet(7);
        tn.addTelConnection(1, 1);
        tn.addTelConnection(3, 1);
        tn.addTelConnection(4, 2);
        tn.addTelConnection(3, 4);
        tn.addTelConnection(2, 6);
        tn.addTelConnection(4, 7);
        tn.addTelConnection(7, 6);
        tn.computeOptTelNet();
        System.out.println(tn.getOptTelNetKosten());
        List<TelVerbindung> tv = tn.getOptTelNet();
        for (TelVerbindung t : tv) {
            System.out.println(t);
        }
        tn.drawOptTelNet(600, 600);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        // Beispiel mit zufälligen Telefonknoten
        TelNet tn2 = new TelNet(1000);
        tn2.generateRandomTelNet(1000, 600, 600);
        tn2.computeOptTelNet();
        System.out.println(tn2.getOptTelNetKosten());
        tn2.drawOptTelNet(600, 600);
    }
}
