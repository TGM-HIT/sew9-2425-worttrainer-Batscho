package bgogoladze.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Diese Klasse namens WortListe beinhaltet einen einen Standartkonstruktor, einen weiteren Konstruktor mit bereits
 * eingetragenem Wort-Bild Paar, jeweils eine Methode zum Adden und eine zum Löschen von Worteinträgen,
 * Zugriffsmethoden für die Collection welche die Wort-Bild Paare speichert und eine Methode zur Darstellung der Paare
 * in textueller Form und eine Methode zur Rückgabe der Länge.
 * Nun diese Klasse ist noch nicht der Worttrainer, sondern wird vom Worttrainer verwendet um Bild-Paare dann in das MVC
 * Konzept einzubinden. Die WortListe ist eine Logikklasse zum speichern der Werte und das möglichst effizient.
 * @author Gogoladze Batschana
 * @version 01-10-2024
 */
public class WortListe {
    private Map<String, String> wortListe;          // Eine Map-Collection zum Speichern der Wort-Bild Paare

    /**
     * Der Standartkonstrukor für die WortListe, welche eine leere HashMap initialisiert
     * und sowohl als Key (Wort) einen String speichert und als Value (URL) auch
     */
    WortListe() {
        this.wortListe = new HashMap<String, String>();
    }

    /**
     * Dies ist der Erweiterte Konstruktor zu welchem man bereits ein Wort-Bild Paare hinzufügen
     * kann welches valide ist.
     * @param wort ist die Bedeutung vom Bild, das Wort
     * @param url  ist das Bild selbst als URL
     */
    WortListe(String wort, String url) {
        this();         // Initialisiert die wortListe-Map durch den Standardkonstruktor
        if(wort != null && url != null && !wort.isEmpty() && !url.isEmpty())
            this.wortListe.put(wort, url);
        else
            throw new IllegalArgumentException("Wort und Bild-URL dürfen nicht null oder leer sein.");
    }

    /**
     * Diese getter-Methode getWortListe ist die Zugriffsmethode zum holen der WortListe
     * @return die WortListe
     */
    public Map<String, String> getWortListe() {
        return this.wortListe;
    }

    /**
     * Diese setter-Methode setWortListe ist die Zugriffsmethode zum setzen eines neuen Wort-Bild Paares
     * @param wortListe ist die zu setzende WortListe
     */
    public void setWortListe(HashMap<String, String> wortListe) {
        if(wortListe != null) this.wortListe = wortListe;
        else throw new NullPointerException("Die zu setzende WortListe ist ungültig (null)!");
    }

    /**
     * Diese Methode getWortEintrag ist zum holen eines einzigen WortEintrags gedacht
     * @param wort ist das zu suchende Wort in der Map mit der dazugehörigen URL
     * @return den einen String Array Eintrag aus dem Wort und der URL
     */
    public String[] getWortEintrag(String wort) {
        if((wort != null && !wort.isEmpty()) && this.wortListe.containsKey(wort)) {
            String[] eintrag = new String[2];
            eintrag[0] = wort;                   // Das Wort (Key) wird gespeichert
            eintrag[1] = this.wortListe.get(wort);    // Die zugehörige URL (Value) wird gespeichert
            return eintrag;
        } else {
            throw new IllegalArgumentException("Das Wort '" + wort + "' ist nicht in der Map enthalten!");
        }
    }
}
