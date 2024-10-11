package bgogoladze.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

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
     * nicht relevant
     * @param speicherpfad nicht verwendet
     */
    public SpeichernUndLaden(String speicherpfad) {}

    /**
     * Diese Methode speichern ist zum serialisierten Speichern eines WortTrainer-Objekts da und speichert diese
     * "Session" in eine Json Datei welche dann später geladen und gelesen werden kann.
     * @param wortTrainer ist die zu speichernde Session für den WortTrainer
     */
    @Override
    public void speichern(WortTrainer wortTrainer) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();                     // Gson-Instanz mit Pretty-Printing
        try (FileWriter fileWriter = new FileWriter(this.getSpeicherpfad())) {          // Öffnet den FileWriter für den Speicherpfad
            gson.toJson(wortTrainer, fileWriter);                                       // Serialisiert das WortTrainer-Objekt und schreibt es in die .json-Datei
            System.out.println("WortTrainer Session erfolgreich gespeichert unter: " + this.speicherpfad);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Speichern der Datei: " + e.getMessage());
        }
    }

    /**
     * Diese Methode laden ist für das entsprechende Laden der gespeicherten WortTrainer-Sitzung gedacht
     * @return die gespeicherte WortTrainer Session
     */
    @Override
    public WortTrainer laden() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(speicherpfad)) {                            // Öffnet den FileReader für den Speicherpfad
            WortTrainer wortTrainer = gson.fromJson(reader, WortTrainer.class);             // Deserialisiert das JSON zurück in ein WortTrainer-Objekt
            System.out.println("WortTrainer Session erfolgreich geladen");
            return wortTrainer;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Laden der Datei: " + e.getMessage());
        }
    }

    /**
     * Getter-Methode des Attributs speicherpfad, welche den
     * aktuellen Wert von speicherpfad liefert
     * @return den aktuellen Wert des Attributs speicherpfad
     */
    public String getSpeicherpfad(){
        return this.speicherpfad += "/worttrainer_session.json";
    }

    /**
     * Setter-Methode des Attributs speicherpfad, welche den
     * neuen Wert für den speicherpfad setzt
     * @param speicherpfad ist der zu setzende neue Pfad
     */
    public void setSpeicherpfad(String speicherpfad) {
        if(speicherpfad == null || speicherpfad.isEmpty()) throw new IllegalArgumentException("Der Speicherpfad darf nicht null oder leer sein!");
        this.speicherpfad = speicherpfad;
    }
}
