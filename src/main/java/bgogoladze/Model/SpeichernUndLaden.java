package bgogoladze.Model;

/**
 * Diese Klasse namens SpeichernUndLaden beinhaltet ein Attribut für den Speicherpfad als String und die zugehörigen
 * Zugriffsmodifikatoren, also getSpeicherPfad und setSpeicherpfad, sowie die wichtigsten Methoden, nämlich das
 * Speichern im Json-Format welches mittels dem Gson Framework ein Objekt in ein Json serialisiert und dieses als
 * Json abspeichern kann, genauso wie eine laden Methode zum Laden der Json Datei. Zusätzlich gibt es zwei
 * Default Speichern und Laden Methoden um es auch schnell zu halten.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */
public class SpeichernUndLaden implements SpeichernUndLadenStrategie {
    private String speicherpfad;


    /**
     * Die abstrakte Speichern Methode
     * @param wortTrainer ist die zu speichernde Session für den WortTrainer
     */
    @Override
    public void speichern(WortTrainer wortTrainer) {

    }

    /**
     * Die abstrakzte Laden Methode
     * @return die gespeicherte WortTrainer Session
     */
    @Override
    public WortTrainer laden() {
        return null;
    }

    /**
     * Getter-Methode des Attributs speicherpfad, welche den
     * aktuellen Wert von speicherpfad liefert
     * @return den aktuellen Wert des Attributs speicherpfad
     */
    public String getKorrekt(){
        return this.speicherpfad;
    }

    /**
     * Setter-Methode des Attributs speicherpfad, welche den
     * neuen Wert für den speicherpfad setzt
     * @param speicherpfad ist der zu setzende neue Pfad
     */
    public void setSpeicherpfad(String speicherpfad) {
        if(speicherpfad.isEmpty() || speicherpfad == null) throw new IllegalArgumentException("Der zu setzende Speicherpfad darf nicht null sein!");
        else this.speicherpfad = speicherpfad;
    }
}
