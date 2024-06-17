package aufgabe4;

/**
 * Klasse für eine Telefonverbindung.
 */
public class TelVerbindung {

    /**
     * Verbindungskosten.
     */
    int c;

    /**
     * Anfangsknoten.
     */
    TelKnoten u;

    /**
     * Endknoten.
     */
    TelKnoten v;

    /**
     * Legt eine neue Telefonverbindung von u nach v mit den Kosten c an.
     * @param u - Anfangsknoten
     * @param v - Endknoten
     * @param c - Verbundungskosten
     */
    public TelVerbindung(TelKnoten u, TelKnoten v, int c) {
        this.c = c;
        this.u = u;
        this.v = v;
    }

    @Override
    public String toString() {
        return "TelVerbindung{ c = " + c + ", \tu = " + u + ", \tv = " + v + " }";
    }
}
