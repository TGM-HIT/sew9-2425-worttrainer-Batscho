import bgogoladze.Model.SpeichernUndLaden;
import bgogoladze.Model.WortListe;
import bgogoladze.Model.WortTrainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Diese Testklasse namens TestWorttrainerPersistence ist zum Testen des Models (SpeichernUndLaden) und beinhaltet
 * mindestens eine Testmethode pro Methode aus der Klassen welche getestet werden soll und auch vielleicht zusätzliche
 * Kombinationen mehrerer Szenarien bezüglich Persistenz.
 * @author Gogoladze Batschana
 * @version 11-10-2024
 */

public class TestWorttrainerPersistence {
    private WortListe wortListe;
    private WortTrainer wortTrainer;
    private SpeichernUndLaden speichernUndLaden;

    @BeforeEach
    public void setup() {
        this.wortListe = new WortListe();
        this.wortListe.addWortEintrag("Papagei", "https://www.vetline.de/sites/default/files/2021-02/wellensittich.jpeg");
        this.wortListe.addWortEintrag("Fische", "https://wallpapers.com/images/hd/tropical-fish-with-corals-krz941d7wbb0jz08.jpg");
        this.wortListe.addWortEintrag("Hamster", "https://blog.wwf.de/wp-content/uploads/2021/12/Feldhamster-Futter-Wangen-0079476299h-1920x1080-c-IMAGO-blickwinkel.jpg");
        this.wortTrainer = new WortTrainer(this.wortListe);
        this.speichernUndLaden = new SpeichernUndLaden("src/main/resources/worttrainer_session.json");
    }

    @AfterEach
    public void deleteTmp() {
        // Lösche die Testdatei nach jedem Test
        File file = new File(this.speichernUndLaden.getSpeicherpfad());
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    @DisplayName("U01 - Testen, ob das Speichern einer WortTrainer-Session überhaupt funktioniert")
    void speichernTest() {
        this.wortTrainer.setAbgefragt(5);
        this.wortTrainer.setKorrekt(4);
        this.speichernUndLaden.speichern(this.wortTrainer);

        File savedFile = new File(this.speichernUndLaden.getSpeicherpfad());
        assertTrue(savedFile.exists(), "Die Datei sollte nach dem Speichern doch eigentlich existieren oder?");
    }

    @Test
    @DisplayName("U02 - Testen der Laden-Funktion einer gespeicherten WortTrainer-Session")
    void ladenTest() {
        this.wortTrainer.setAbgefragt(100);
        this.wortTrainer.setKorrekt(99);
        this.speichernUndLaden.speichern(this.wortTrainer);
        WortTrainer loadedWortTrainer = this.speichernUndLaden.laden();

        assertEquals(100, loadedWortTrainer.getAbgefragt(), "Die Anzahl der abgefragten Wörter sollte doch gleich sein wie die gespeicherte json?");
        assertEquals(99, loadedWortTrainer.getKorrekt(), "Die Anzahl der korrekt beantworteten Wörter sollte doch auch gleich sein wie in der json-Datei?");
    }

    @Test
    @DisplayName("U03 - Testen der Getter und Setter des Speicherpfads")
    void getsetPathTest() {
        this.speichernUndLaden.setSpeicherpfad("./newPath.json");

        assertTrue(this.speichernUndLaden.getSpeicherpfad().contains("newPath.json"), "Der Speicherpfad sollte doch den neuen Dateinamen enthalten oder nicht?");

        this.speichernUndLaden.speichern(this.wortTrainer);
        File savedFile = new File(this.speichernUndLaden.getSpeicherpfad());

        assertTrue(savedFile.exists(), "Die Datei sollte nach dem Speichern doch eigentlich mit dem neuen Dateinamen existieren oder?");

    }
}
