package bgogoladze.Model;

/**
 * Dieses Interface namens SpeichernUndLadenStrategie beinhaltet eine Methode zum Speichern und eine Methode zum Laden
 * der WortTrainer-Sessions. Aufgabe:
 * Eine Worttrainer-Session (bestehend aus den zur Verfügung stehenden Wort-Bild-Paaren, dem aktuell ausgewählten Paar
 * (falls vorhanden) sowie der aktuellen Statistik) soll auch gespeichert werden können. Die genaue Umsetzung ist großteils dir überlassen:
 * wähle ein beliebiges Speicherformat (z.B. selbst festgelegt, Java-Serialisierung, JSON, XML, SQLite, ...)
 * benutze beliebige dafür geeignete Bibliotheken, falls notwendig (z.B. org.json:json, eine von zahlreichen XML-Libraries, org.xerial:sqlite-jdbc)
 * wähle zur Integration der Persistierung in deine Anwendung ein sinnvolles Pattern, sodass die Speicherstrategie austauschbar bleibt.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */
public interface SpeichernUndLadenStrategie {
    /**
     * Die abstrakte Speichern Methode
     * @param wortTrainer ist die zu speichernde Session für den WortTrainer
     */
    void speichern(WortTrainer wortTrainer);

    /**
     * Die abstrakzte Laden Methode
     * @return die gespeicherte WortTrainer Session
     */
    WortTrainer laden();
}
