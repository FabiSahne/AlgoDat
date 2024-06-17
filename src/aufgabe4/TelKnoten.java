package aufgabe4;

/**
 * Klasse für einen Telefonknoten.
 */
public class TelKnoten {

    /**
     * x-Koordinate.
     */
    final int x;

    /**
     * y-Koordinate.
     */
    final int y;

    /**
     * Legt einen neuen Telefonknoten mit den Koordinaten (x,y) an.
     * @param x - x-Koordinate
     * @param y - y-Koordinate
     */
    public TelKnoten(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof TelKnoten other) {
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (x << 16) | y;
    }

    @Override
    public String toString() {
        return "TelKnoten{ x = " + x + ", \ty = " + y + " }";
    }
}
